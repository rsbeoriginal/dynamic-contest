package com.game.dynamiccontest.controller;

import com.game.dynamiccontest.dto.*;
import com.game.dynamiccontest.entity.ContestQuestion;
import com.game.dynamiccontest.services.ContestQuestionService;
import com.game.dynamiccontest.services.ContestService;
import com.game.dynamiccontest.utils.FailException;
import com.game.dynamiccontest.utils.ResponseConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/contest/{contestId}/questions")
public class ContestQuestionController {

    @Autowired
    ContestQuestionService contestQuestionService;

    @Autowired
    ContestService contestService;

//    @PostMapping("/")
//    public ResponseDTO<List<ContestQuestionDTO>> addQuestions(@RequestBody RequestDTO<List<ContestQuestionDTO>> requestDTO, @PathVariable("contestId") String contestId){
//
//        ResponseDTO<List<ContestQuestionDTO>> responseDTO = new ResponseDTO<>();
//
//        if(verifyUser(requestDTO.getUserId())) {
//            boolean transactionSuccess = true;
//            List<ContestQuestionDTO> contestQuestionDTOList = new ArrayList<>();
//            for (ContestQuestionDTO contestQuestionDTO : requestDTO.getRequest()) {
//                if(contestQuestionService.getContestQuestionById(contestId, contestQuestionDTO.getQuestionId()) == null) {
//                    if (contestQuestionDTO.getContest() == null)
//                        contestQuestionDTO.setContest(contestService.getContestById(contestId));
//                    ContestQuestion contestQuestion = new ContestQuestion();
//                    BeanUtils.copyProperties(contestQuestionDTO, contestQuestion);
//                    try {
//                        int questionSequence=contestQuestionService.getNextQuestionNumber(contestId);
//                        contestQuestion.setQuestionSequence(questionSequence+1);
//                        ContestQuestionDTO contestQuestionDTO1 = new ContestQuestionDTO();
//                        BeanUtils.copyProperties(contestQuestionService.add(contestQuestion), contestQuestionDTO1);
//                        contestQuestionDTOList.add(contestQuestionDTO1);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else {
//                    contestQuestionService.deleteContestQuestionByContestId(contestId);
//                    transactionSuccess = false;
//                    break;
//                }
//            }
//            if(transactionSuccess) {
//                responseDTO.setStatus(ResponseConstants.SUCCESS);
//                responseDTO.setResponse(contestQuestionDTOList);
//            }
//            else{
//                responseDTO.setStatus(ResponseConstants.FAIL);
//                responseDTO.setErrorMessage("Error while adding questions");
//            }
//        }
//        else{
//            responseDTO.setStatus(ResponseConstants.FAIL);
//            responseDTO.setErrorMessage("Auth Failed");
//        }
//        return responseDTO;
//    }

    @PostMapping("/")
    public ResponseDTO<ContestQuestionDTO> addQuestion(@RequestBody RequestDTO<ContestQuestionDTO> requestDTO, @PathVariable("contestId") String contestId){

        ResponseDTO<ContestQuestionDTO> responseDTO = new ResponseDTO<>();

        if(verifyUser(requestDTO.getUserId())) {
            try {
                ContestQuestionDTO contestQuestionDTO1 = new ContestQuestionDTO();
                BeanUtils.copyProperties(contestQuestionService.add(contestId,requestDTO.getRequest()), contestQuestionDTO1);
                responseDTO.setStatus(ResponseConstants.SUCCESS);
                responseDTO.setResponse(contestQuestionDTO1);
            }catch (FailException e){
                responseDTO.setStatus(ResponseConstants.FAIL);
                responseDTO.setErrorMessage(e.getMessage());
            }catch (Exception e){
                responseDTO.setStatus(ResponseConstants.ERROR);
                responseDTO.setErrorMessage(e.getMessage());
            }
        }
        else{
            responseDTO.setStatus(ResponseConstants.FAIL);
            responseDTO.setErrorMessage("Auth Failed");
        }
        return responseDTO;
    }

    @GetMapping("/")
    public ResponseDTO<List<ContestQuestionDTO>> getQuestions(@PathVariable("contestId") String contestId){
        List<ContestQuestion> contestQuestions = contestQuestionService.getAllQuestions(contestId);
        ResponseDTO<List<ContestQuestionDTO>> responseDTO = new ResponseDTO<>();
        List<ContestQuestionDTO> contestQuestionDTOList = new ArrayList<>();
        try {
            for (ContestQuestion contestQuestion : contestQuestions) {
                ContestQuestionDTO contestQuestionDTO = new ContestQuestionDTO();
                BeanUtils.copyProperties(contestQuestion, contestQuestionDTO);
                contestQuestionDTOList.add(contestQuestionDTO);
            }
            responseDTO.setStatus(ResponseConstants.SUCCESS);
            responseDTO.setResponse(contestQuestionDTOList);
        }
        catch (Exception e){
            responseDTO.setStatus(ResponseConstants.ERROR);
            responseDTO.setErrorMessage("Failed while retrieving Questions");
        }
        return  responseDTO;
    }

    private boolean verifyUser(String userId) {
        return true;
    }
}
