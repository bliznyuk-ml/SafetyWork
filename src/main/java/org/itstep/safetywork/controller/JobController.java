package org.itstep.safetywork.controller;


import lombok.RequiredArgsConstructor;

import org.itstep.safetywork.model.Job;
import org.itstep.safetywork.model.Phone;
import org.itstep.safetywork.repository.JobRepository;
import org.itstep.safetywork.repository.PhoneRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class JobController {
    private final JobRepository jobRepository;
    private final PhoneRepository phoneRepository;

    @GetMapping("/job")
    public String showForm(Model model){
        model.addAttribute("workPhone", jobRepository.findAll());
        return "testForm";
    }

    @PostMapping("/job")
    public String savePhone(String job, String[]phone){
        String addedJob = job;
        Job job1 = new Job(addedJob);
        jobRepository.save(job1);
//        String[] phones;
//        phones = Arrays.copyOf(phone, phone.length);
        for (String p : phone){
            Phone phone1 = new Phone(p);
            phone1.setJob(job1);
            phoneRepository.save(phone1);
            System.out.println(p);
        }

        return "redirect:/job";
    }
}

