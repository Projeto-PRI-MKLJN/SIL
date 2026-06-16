package com.sil.informatica.modules.admin;

import com.sil.informatica.modules.sign.Sign;
import com.sil.informatica.modules.sign.SignService;
import com.sil.informatica.modules.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/// Controller administrativo para gestão do glossário de sinais técnicos.
///
/// Este controlador permite que administradores criem, editem e excluam termos do sistema SIL-Informatica.
@Controller
@RequestMapping("/admin/signs")
public class AdminSignController {

    private final SignService signService;
    private final CategoryService categoryService;

    @Autowired
    public AdminSignController(SignService signService, CategoryService categoryService) {
        this.signService = signService;
        this.categoryService = categoryService;
    }

    /// Lista todos os sinais no painel administrativo.
    ///
    /// @param model O modelo do Spring.
    /// @return O caminho da view do índice administrativo.
    @GetMapping
    public String listSigns(Model model) {
        model.addAttribute("signs", signService.findAll());
        model.addAttribute("sign", new Sign());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/signs/index";
    }

    /// Processa a criação ou atualização de um sinal.
    @PostMapping
    public String saveSign(@Valid Sign sign, BindingResult result, Model model, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("signs", signService.findAll());
            model.addAttribute("sign", sign);
            model.addAttribute("categories", categoryService.findAll());
            return "admin/signs/index";
        }
        boolean isNew = sign.getId() == null;
        signService.save(sign);
        redirectAttributes.addFlashAttribute("success", isNew ? "Sinal criado com sucesso!" : "Sinal atualizado com sucesso!");
        return "redirect:/admin/signs";
    }

    /// Remove um sinal do sistema.
    ///
    /// @param id ID do sinal.
    /// @return Redirecionamento para a página inicial administrativa.
    @GetMapping("/delete/{id}")
    public String deleteSign(@PathVariable Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        signService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Sinal removido com sucesso!");
        return "redirect:/admin/signs";
    }
}
