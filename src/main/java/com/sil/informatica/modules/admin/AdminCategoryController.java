package com.sil.informatica.modules.admin;

import com.sil.informatica.modules.category.Category;
import com.sil.informatica.modules.category.CategoryService;
import com.sil.informatica.modules.sign.SignService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;
    private final SignService signService;

    @Autowired
    public AdminCategoryController(CategoryService categoryService, SignService signService) {
        this.categoryService = categoryService;
        this.signService = signService;
    }

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("category", new Category());
        return "admin/categories/index";
    }

    @PostMapping
    public String saveCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/categories/index";
        }

        // Check for duplicate name
        boolean nameExists = categoryService.findByName(category.getName())
                .map(c -> !c.getId().equals(category.getId()))
                .orElse(false);

        if (nameExists) {
            result.rejectValue("name", "duplicate", "Já existe uma categoria com este nome.");
            model.addAttribute("categories", categoryService.findAll());
            return "admin/categories/index";
        }

        boolean isNew = category.getId() == null;
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("success", isNew ? "Categoria criada com sucesso!" : "Categoria atualizada com sucesso!");
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Prevent deletion if any sign belongs to this category
        boolean hasSigns = signService.findAll().stream()
                .anyMatch(sign -> sign.getCategory().getId().equals(id));

        if (hasSigns) {
            redirectAttributes.addFlashAttribute("error", "Não é possível remover uma categoria que possui sinais associados!");
        } else {
            categoryService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Categoria removida com sucesso!");
        }
        return "redirect:/admin/categories";
    }
}
