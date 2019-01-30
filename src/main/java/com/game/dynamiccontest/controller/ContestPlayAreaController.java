package com.game.dynamiccontest.controller;

import com.game.dynamiccontest.dto.ContestPlayAreaDTO;
import com.game.dynamiccontest.dto.QuestionDetailDTO;
import com.game.dynamiccontest.dto.RequestDTO;
import com.game.dynamiccontest.dto.ResponseDTO;
import com.game.dynamiccontest.services.ContestPlayAreaService;
import com.game.dynamiccontest.utils.ResponseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/contests/{contestId}/play")
public class ContestPlayAreaController {

    @Autowired
    ContestPlayAreaService contestPlayAreaService;

//    @PostMapping("/nextQuestion")
//    public ResponseDTO<QuestionDetailDTO> nextQuestion(@RequestBody RequestDTO<String> requestDTO){
//        ResponseDTO<QuestionDetailDTO> responseDTO = new ResponseDTO<>();
//        if(verifyUser(requestDTO.getUserId())){
//            QuestionDetailDTO questionDetailDTO = contestPlayAreaService.getNextQuestion();
//        }else {
//
//        }
//        return responseDTO;
//    }
    @PostMapping("/submit")
    public ResponseDTO<String> submit(@PathVariable("contestId") String contestId, @RequestBody RequestDTO<ContestPlayAreaDTO> requestDTO){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(verifyUser(requestDTO.getUserId())){
            try{
                contestPlayAreaService.submitAnswer(requestDTO.getRequest());
                responseDTO.setStatus(ResponseConstants.SUCCESS);
            }catch (Exception e){
                responseDTO.setStatus(ResponseConstants.ERROR);
                responseDTO.setResponse(e.getMessage());
            }
        }else {
            responseDTO.setStatus(ResponseConstants.FAIL);
            responseDTO.setResponse("Auth fail");
        }
        return responseDTO;
    }

    @PostMapping("/finish")
    public ResponseDTO<String> finish(@PathVariable("contestId") String contestId, @RequestBody RequestDTO<String> requestDTO){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(verifyUser(requestDTO.getUserId())){
            try {
                contestPlayAreaService.finishContest(contestId,requestDTO.getUserId());
                responseDTO.setStatus(ResponseConstants.SUCCESS);
            }catch (Exception e){
                responseDTO.setStatus(ResponseConstants.ERROR);
                responseDTO.setResponse(e.getMessage());
            }
        }else{
            responseDTO.setStatus(ResponseConstants.FAIL);
            responseDTO.setResponse("Auth fail");
        }
        return responseDTO;
    }


    private boolean verifyUser(String userId) {
        return true;
    }

}
