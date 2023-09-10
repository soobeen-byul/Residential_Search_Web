package beaver.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import beaver.backend.domain.Trans;
import beaver.backend.dto.TransResponse;
import beaver.backend.service.TransService;

@RequiredArgsConstructor
@Controller
public class TransViewController {
    private final TransService transService;

    @GetMapping("/sqltest/{id}")
    public String getTrans(@PathVariable Long id, Model model) {
        Trans trans = transService.findById(id);
        model.addAttribute("trans", new TransResponse(trans));

        return "trans"; // trans.html 라는 뷰 조회
    }
    
}
