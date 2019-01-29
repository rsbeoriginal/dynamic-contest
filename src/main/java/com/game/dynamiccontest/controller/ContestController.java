package com.game.dynamiccontest.controller;

import com.game.dynamiccontest.dto.ContestDTO;
import com.game.dynamiccontest.dto.RequestDTO;
import com.game.dynamiccontest.dto.ResponseDTO;
import com.game.dynamiccontest.dto.ResponseListDTO;
import com.game.dynamiccontest.entity.Contest;
import com.game.dynamiccontest.services.ContestService;
import com.game.dynamiccontest.utils.FailException;
import com.game.dynamiccontest.utils.ResponseConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contests")
public class ContestController {

    @Autowired
    ContestService contestService;

    @PostMapping("/")
    public ResponseDTO<ContestDTO> add(@RequestBody RequestDTO<ContestDTO> requestDTO){

        ResponseDTO<ContestDTO> responseDTO = new ResponseDTO<>();
        if(verifyUser(requestDTO.getUserId())) {
            Contest contest = new Contest();
            BeanUtils.copyProperties(requestDTO.getRequest(), contest);
            try {
                ContestDTO contestDTO = new ContestDTO();
                BeanUtils.copyProperties(contestService.add(contest), contestDTO);
                responseDTO.setStatus(ResponseConstants.SUCCESS);
                responseDTO.setResponse(contestDTO);
            } catch (FailException e) {
                responseDTO.setStatus(ResponseConstants.FAIL);
                responseDTO.setErrorMessage(e.getMessage());
            } catch (Exception e) {
                responseDTO.setStatus(ResponseConstants.ERROR);
                responseDTO.setErrorMessage(e.getMessage());
            }
        }else {
            responseDTO.setStatus(ResponseConstants.FAIL);
            responseDTO.setErrorMessage("Auth failed");
        }
        return responseDTO;
    }

    @GetMapping("/")
    public ResponseListDTO<ContestDTO> getAll(){
        ResponseListDTO<ContestDTO> responseListDTO = new ResponseListDTO<>();
        try {
            List<ContestDTO> contestDTOList = new ArrayList<>();
            for (Contest c: contestService.getAll()) {
                ContestDTO contestDTO = new ContestDTO();
                BeanUtils.copyProperties(c,contestDTO);
                contestDTOList.add(contestDTO);
            }
            responseListDTO.setResponse(contestDTOList);
            responseListDTO.setStatus(ResponseConstants.SUCCESS);
        }catch (Exception e){
            responseListDTO.setStatus(ResponseConstants.ERROR);
            responseListDTO.setErrorMessage(e.getMessage());
        }
        return responseListDTO;
    }

    @GetMapping("/{contestId}")
    public ResponseDTO<ContestDTO> getContestById(@PathVariable("contestId") String contestId){
        ResponseDTO<ContestDTO> responseDTO = new ResponseDTO<>();
        try {
            ContestDTO contestDTO = new ContestDTO();
            BeanUtils.copyProperties(contestService.getContestById(contestId),contestDTO);
            responseDTO.setResponse(contestDTO);
            responseDTO.setStatus(ResponseConstants.SUCCESS);
        }catch (Exception e){
            responseDTO.setStatus(ResponseConstants.ERROR);
            responseDTO.setStatus(e.getMessage());
        }
        return responseDTO;
    }

    private boolean verifyUser(String userId) {
        return true;
    }
}
