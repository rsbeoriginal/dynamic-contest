package com.game.dynamiccontest.services.impl;

import com.game.dynamiccontest.dto.ContestQuestionDTO;
import com.game.dynamiccontest.dto.DynamicQuestionDTO;
import com.game.dynamiccontest.dto.OptionDTO;
import com.game.dynamiccontest.dto.QuestionDetailDTO;
import com.game.dynamiccontest.entity.ContestQuestion;
import com.game.dynamiccontest.repository.ContestQuestionRepository;
import com.game.dynamiccontest.services.ContestQuestionService;
import com.game.dynamiccontest.services.ContestService;
import com.game.dynamiccontest.utils.FailException;
import com.game.dynamiccontest.utils.MicroservicesURL;
import com.game.dynamiccontest.utils.ResponseConstants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly =  true, propagation = Propagation.REQUIRES_NEW)
public class ContestQuestionServiceImpl implements ContestQuestionService {

    @Autowired
    ContestQuestionRepository contestQuestionRepository;

    @Autowired
    ContestService contestService;

    @Autowired
    RestTemplate restTemplate;

    @Override
    @Transactional(readOnly = false)
    public ContestQuestion add(String contestId, ContestQuestionDTO contestQuestionDTO) throws FailException {

        ContestQuestion contestQuestionCreated = null;
        if(getContestQuestionById(contestId, contestQuestionDTO.getQuestionId()) == null) {
            if (contestQuestionDTO.getContest() == null)
                contestQuestionDTO.setContest(contestService.getContestById(contestId));
            ContestQuestion contestQuestion = new ContestQuestion();
            BeanUtils.copyProperties(contestQuestionDTO, contestQuestion);
            try {
                int questionSequence = getNextQuestionNumber(contestId);
                contestQuestion.setQuestionSequence(questionSequence + 1);
                ContestQuestionDTO contestQuestionDTO1 = new ContestQuestionDTO();
//                BeanUtils.copyProperties(contestQuestionService.add(contestQuestion), contestQuestionDTO1);

                //TODO:DATE_TIME AS PER QS SUBMIT
                Date date = new Date();
                contestQuestion.setStartTime(date.getTime());

                contestQuestionCreated =  contestQuestionRepository.save(contestQuestion);

                //TODO:DUMMY DATA
                QuestionDetailDTO questionDetailDTO = new QuestionDetailDTO();
                questionDetailDTO.setQuestionId(""+contestQuestion.getQuestionSequence());
                OptionDTO optionDTO = new OptionDTO();
                optionDTO.setOptionContent("OPTION VALUE ");
                List<OptionDTO> optionDTOList = new ArrayList<>();
                optionDTOList.add(optionDTO);
                optionDTOList.add(optionDTO);
                optionDTOList.add(optionDTO);
                optionDTOList.add(optionDTO);
                questionDetailDTO.setOptionDTOList(optionDTOList);
                questionDetailDTO.setQuestionName("WHat is this");
                questionDetailDTO.setQuestionContent("this is actual questions");
                //TODO:REMOVE CODE
                sendQuestionToFirebaseDatabase(contestId,contestQuestion.getQuestionId(),contestQuestion.getQuestionSequence(),contestQuestion.getLast());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            throw new FailException("Error while adding questions");
        }
        return contestQuestionCreated;
    }

    @Override
    public List<ContestQuestion> getAllQuestions(String contestId) {
        return (List)contestQuestionRepository.findContestQuestionByContest_ContestId(contestId);
    }

    @Override
    public ContestQuestion getContestQuestionById(String contestId, String questionId) {
        return contestQuestionRepository.findContestQuestionByContest_ContestIdAndQuestionId(contestId, questionId);
    }

    @Override
    public int getNextQuestionNumber(String contestId) {
        return contestQuestionRepository.findNextQuestionNumber(contestId);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteContestQuestionByContestId(String contestId) {
        contestQuestionRepository.deleteContestQuestionByContest_ContestId(contestId);
    }


    public void sendQuestionToFirebaseDatabase(String contestId, String questionId, Integer questionSequence, Boolean last) {

        if(questionSequence == 1) {
            DatabaseReference currentQuestionRef = FirebaseDatabase.getInstance().getReference().child("Dynamic_Contest")
                    .child(contestId).child("currentQuestion");
            currentQuestionRef.setValueAsync(questionSequence);
        }

        QuestionDetailDTO questionDetailDTO = getQuestionFromServer(questionId);
        DynamicQuestionDTO dynamicQuestionDTO = new DynamicQuestionDTO();
        dynamicQuestionDTO.setQuestionDetail(questionDetailDTO);
        dynamicQuestionDTO.setLast(last);
        dynamicQuestionDTO.setQuestionSequence(questionSequence);


        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Dynamic_Contest")
                .child(contestId)
                .child("questions")
                .child("" + questionSequence);
        ref.setValueAsync(dynamicQuestionDTO);
    }

    public QuestionDetailDTO getQuestionFromServer(String questionId)
    {
        String URL=MicroservicesURL.SCREENED_QUESTION_BASE_URL + MicroservicesURL.GET_QUESTION_BY_ID +questionId;
        System.out.println("url getQuestionById: " + URL);
        ResponseEntity<QuestionDetailDTO> response = restTemplate.getForEntity(URL, QuestionDetailDTO.class);
        System.out.println(response.getBody());
        return response.getBody();

    }



}
