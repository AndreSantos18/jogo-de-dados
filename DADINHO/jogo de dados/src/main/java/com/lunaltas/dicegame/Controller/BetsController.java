package com.lunaltas.dicegame.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.lunaltas.dicegame.domain.Bet;
import com.lunaltas.dicegame.domain.User;
import com.lunaltas.dicegame.service.BetService;
import com.lunaltas.dicegame.service.UserService;
import jakarta.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/bets")
public class BetsController {

  @Autowired
  private BetService betService;

  @Autowired
  private UserService userService;

  @GetMapping("/index")
  public String index(ModelMap model) {
    model.addAttribute("bets", betService.findAll());
    model.addAttribute("size", betService.findAll().size());
    return "/bets/index";
  }

  @GetMapping("/new")
  public String newBet(ModelMap model) {
    model.addAttribute("bet", new Bet());
    return "/bets/new";
  }

  @PostMapping("/create")
  public String create(@Valid Bet bet, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      return "/bets/new";
    }
    User currentUser = userService.getCurrentUser();
    bet.setOwner(currentUser);
    bet.getParticipants().add(currentUser);
    betService.save(bet);
    redirectAttributes.addFlashAttribute("success", "Aposta criada com sucesso!");
    return "redirect:/bets/show/" + bet.getId();
  }

  @GetMapping("/show/{id}")
  public String show(@PathVariable Long id, ModelMap model) {
    Bet bet = betService.findById(id);
    model.addAttribute("bet", bet);
    model.addAttribute("isOwner", userService.getCurrentUser().getId().equals(bet.getOwner().getId()));
    return "/bets/show";
  }

  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Long id, ModelMap model, RedirectAttributes redirectAttributes) {
    Bet bet = betService.findById(id);
    User currentUser = userService.getCurrentUser();
    if (!bet.getOwner().getId().equals(currentUser.getId())) {
      redirectAttributes.addFlashAttribute("error", "Apenas o dono da aposta pode editar.");
      return "redirect:/bets/show/" + id;
    }
    model.addAttribute("bet", bet);
    return "/bets/edit";
  }

  @PutMapping("/update/{id}")
  public String update(@PathVariable Long id, @Valid Bet bet, BindingResult result, RedirectAttributes redirectAttributes) {
    Bet existing = betService.findById(id);
    User currentUser = userService.getCurrentUser();
    if (!existing.getOwner().getId().equals(currentUser.getId())) {
      redirectAttributes.addFlashAttribute("error", "Apenas o dono da aposta pode atualizar.");
      return "redirect:/bets/show/" + id;
    }
    if (result.hasErrors()) {
      return "/bets/edit";
    }
    existing.setTitle(bet.getTitle());
    existing.setDescription(bet.getDescription());
    betService.update(existing);
    redirectAttributes.addFlashAttribute("success", "Aposta atualizada!");
    return "redirect:/bets/show/" + id;
  }

  @DeleteMapping("/delete/{id}")
  public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    Bet bet = betService.findById(id);
    User currentUser = userService.getCurrentUser();
    if (!bet.getOwner().getId().equals(currentUser.getId())) {
      redirectAttributes.addFlashAttribute("error", "Apenas o dono pode deletar esta aposta.");
      return "redirect:/bets/show/" + id;
    }
    betService.delete(id);
    redirectAttributes.addFlashAttribute("success", "Aposta deletada!");
    return "redirect:/bets/index";
  }

  @PostMapping("/drawWinner/{id}")
  public String drawWinner(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    Bet bet = betService.findById(id);
    User currentUser = userService.getCurrentUser();

    if (!bet.getOwner().getId().equals(currentUser.getId())) {
      redirectAttributes.addFlashAttribute("error", "Somente o dono pode sortear o vencedor.");
      return "redirect:/bets/show/" + id;
    }

    List<User> participants = bet.getParticipants();
    if (participants.isEmpty()) {
      redirectAttributes.addFlashAttribute("error", "Nenhum participante na aposta!");
      return "redirect:/bets/show/" + id;
    }

    Random random = new Random();
    User winner = participants.get(random.nextInt(participants.size()));
    bet.setWinner(winner);
    betService.update(bet);
    redirectAttributes.addFlashAttribute("success", "Vencedor sorteado: " + winner.getUsername());
    return "redirect:/bets/show/" + id;
  }
}
