package com.game.dynamiccontest.controller;

import com.game.dynamiccontest.dto.ContestSubscriptionDTO;
import com.game.dynamiccontest.dto.RequestDTO;
import com.game.dynamiccontest.dto.ResponseDTO;
import com.game.dynamiccontest.dto.ResponseListDTO;
import com.game.dynamiccontest.services.ContestSubscribeService;
import com.game.dynamiccontest.utils.FailException;
import com.game.dynamiccontest.utils.ResponseConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/contests/{contestId}")
public class ContestSubscriptionController {

    @Autowired
    ContestSubscribeService contestSubscribeService;

    @PostMapping("/subscribe")
    public ResponseDTO<ContestSubscriptionDTO> subscribeToContest(@PathVariable("contestId") String contestId, @RequestBody RequestDTO<String> requestDTO){
        ResponseDTO<ContestSubscriptionDTO> responseDTO = new ResponseDTO<>();
        if(verifyUser(requestDTO.getUserId())) {
            try {
                ContestSubscriptionDTO contestSubscriptionDTO = new ContestSubscriptionDTO();
                BeanUtils.copyProperties(contestSubscribeService.subscribeToContest(contestId, requestDTO.getUserId()), contestSubscriptionDTO);
                responseDTO.setResponse(contestSubscriptionDTO);
                responseDTO.setStatus(ResponseConstants.SUCCESS);
            } catch (FailException e) {
                responseDTO.setStatus(ResponseConstants.FAIL);
                responseDTO.setErrorMessage(e.getMessage());
            } catch (Exception e) {
                responseDTO.setStatus(ResponseConstants.ERROR);
                responseDTO.setErrorMessage(e.getMessage());
            }
        }else{
            responseDTO.setStatus(ResponseConstants.ERROR);
            responseDTO.setErrorMessage("Auth failed");
        }
        return responseDTO;
    }

    @GetMapping("/leaderboard")
    public ResponseListDTO<ContestSubscriptionDTO> getLeaderboard(@PathVariable("contestId") String contestId){
        ResponseListDTO<ContestSubscriptionDTO> responseListDTO = new ResponseListDTO<>();
        try{
            responseListDTO.setResponse(contestSubscribeService.getLearboard(contestId));
            responseListDTO.setStatus(ResponseConstants.SUCCESS);
        }catch (FailException e){
            responseListDTO.setStatus(ResponseConstants.FAIL);
            responseListDTO.setErrorMessage(e.getMessage());
        }catch (Exception e){
            responseListDTO.setStatus(ResponseConstants.ERROR);
            responseListDTO.setErrorMessage(e.getMessage());
        }
        return responseListDTO;
    }

    private boolean verifyUser(String userId) {
        return true;
    }
}
