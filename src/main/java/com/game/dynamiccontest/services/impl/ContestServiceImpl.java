package com.game.dynamiccontest.services.impl;

import com.contest.notificationProducer.dto.Header;
import com.contest.notificationProducer.notificationEnum.NotificationMedium;
import com.contest.notificationProducer.notificationEnum.NotificationType;
import com.contest.notificationProducer.producer.ContestProducer;
import com.game.dynamiccontest.entity.Contest;
import com.game.dynamiccontest.repository.ContestRepository;
import com.game.dynamiccontest.services.ContestService;
import com.game.dynamiccontest.utils.FailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
public class ContestServiceImpl implements ContestService {

    @Autowired
    ContestRepository contestRepository;

    @Autowired
    ContestProducer contestProducer;

    @Override
    @Transactional(readOnly = false)
    public Contest add(Contest contest) throws FailException,Exception {
        contestRepository.changeContestActiveAttribute(false);
        Contest contestCreated = contestRepository.save(contest);
        if(contestCreated == null){
            throw new FailException("Unable to create to contest");
        }
        Thread notificationThread = new Thread(new NotificationThread(contest));
        notificationThread.start();
        return contestCreated;
    }

    private void sendNotification(Contest contest) {
        //sending notification....

        try {
            Header header = new Header();
            com.contest.notificationProducer.dto.Contest contestN = new com.contest.notificationProducer.dto.Contest();
            contestN.setContestId(contest.getContestId());
            contestN.setContestName(contest.getContestName());
            List<NotificationMedium> notificationMediumList = new ArrayList<>();
            notificationMediumList.add(NotificationMedium.EMAIL);
            header.setNotificationMedium(notificationMediumList);
            header.setNotificationType(NotificationType.CONTEST);
            header.setTimeStamp(new Date().toString());
            header.setNotificationTypeBody(contestN);
            header.setReceiver("");
            contestProducer.send(header);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        //..................
    }

    @Override
    public List<Contest> getAll() throws Exception {
        return contestRepository.getAll();
    }

    @Override
    public Contest getContestById(String contestId) {
        return contestRepository.findOne(contestId);
    }

    @Override
    public Contest getActiveContest() throws FailException {
        Contest contest = contestRepository.getActiveContest();
        if(null == contest) {
            throw new FailException("No active contest");
        }
        return contest;
    }


    public class NotificationThread implements Runnable {

        Contest contest;

        public NotificationThread(Contest contest){
            this.contest=contest;
        }

        @Override
        public void run() {
            sendNotification(contest);
        }
    }

}
