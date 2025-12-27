package com.medilabo.RiskService.riskController;

import com.medilabo.RiskService.dto.RiskReportDto;
import com.medilabo.RiskService.service.RiskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// permet d'afficher les endpints de calcul de risque
@RestController
@RequestMapping("/risk")
public class RiskRestController {
    // appel le calcul de risque
        private final RiskService riskService;
        //injection de dépendances
        public RiskRestController(RiskService riskService) {
            this.riskService = riskService;
        }

        //vérifie que le service répond
        @GetMapping({"", "/"})
        public ResponseEntity<String> home() {
            return ResponseEntity.ok("Risk service is UP. Use http://localhost:8090/patients/ui/liste to reach risk according patient.");
        }

        //calcule et retourne rapport de risque
        @GetMapping("/{patientId}")
        public ResponseEntity<RiskReportDto> getRisk(@PathVariable Long patientId) {
            RiskReportDto report = riskService.assessRisk(patientId);
            return ResponseEntity.ok(report);
        }

}
