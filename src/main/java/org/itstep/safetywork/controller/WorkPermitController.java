package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.HighRiskWork;
import org.itstep.safetywork.model.Machinery;
import org.itstep.safetywork.repository.HighRiskWorkRepository;
import org.itstep.safetywork.repository.MachineryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WorkPermitController {
    private final MachineryRepository machineryRepository;
    private final HighRiskWorkRepository highRiskWorkRepository;

    @GetMapping("/machinery_list")
    public List<Machinery> getMachineryList(){
        return machineryRepository.findAll();
    }

//    @GetMapping("/high_risk_work")
//    public ResponseEntity <List<HighRiskWork>> findAllHighRisk(){
//        List<HighRiskWork> highRiskWorkList = highRiskWorkRepository.findAll();
//        ResponseEntity<List<HighRiskWork>> response;
//        if(!highRiskWorkList.isEmpty()){
//            response = ResponseEntity.of(Optional.of(highRiskWorkList));
//        } else {
//
//        }
//
//        return (ResponseEntity<List<HighRiskWork>>);
//    }
}
