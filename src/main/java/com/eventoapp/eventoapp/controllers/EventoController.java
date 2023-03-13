package com.eventoapp.eventoapp.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.models.Evento;
import com.eventoapp.eventoapp.repositories.ConvidadoRepository;
import com.eventoapp.eventoapp.repositories.EventoRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository er;

    @Autowired
    private ConvidadoRepository cr;

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
    public String form() {
        return "evento/formEvento";
    }

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
    public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Verifique os campos!");
            return "redirect:/cadastrarEvento";
        }

        er.save(evento);
        attributes.addFlashAttribute("message", "Evento cadastrado com sucesso!!!");

        return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/")
    public ModelAndView listaEventos() {
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = er.findAll();

        mv.addObject("eventos", eventos);

        return mv;
    }

    @RequestMapping(value = "/evento/{id}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("id") UUID id) {
        Optional<Evento> evento = er.findById(id);

        ModelAndView mv = new ModelAndView("evento/detalhesEvento");

        Evento eventoObj = evento.get();

        mv.addObject("evento", eventoObj);

        Iterable<Convidado> convidados = cr.findByEvento(eventoObj);

        mv.addObject("convidados", convidados);

        return mv;
    }

    @RequestMapping(value = "/evento/{id}", method = RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("id") UUID id, @Valid Convidado convidado, BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Verifique os campos!");

            return "redirect:/evento/" + id.toString();
        }

        Optional<Evento> evento = er.findById(id);

        convidado.setEvento(evento.get());
        cr.save(convidado);

        attributes.addFlashAttribute("message", "Convidado adicionado com sucesso!!");

        return "redirect:/evento/" + id.toString();
    }

    @Transactional
    @RequestMapping(value = "/deletarEvento/{id}")
    public String deletarEvento(@PathVariable("id") UUID id) {
        Optional<Evento> optionalEvento = er.findById(id);

        Evento evento = optionalEvento.get();

        er.delete(evento);

        return "redirect:/";
    }

    @Transactional
    @RequestMapping("/deletarConvidado/{id}")
    public String deletarConvidado(@PathVariable("id") UUID id) {
        Optional<Convidado> convidado = cr.findById(id);
        Evento evento = convidado.get().getEvento();

        cr.delete(convidado.get());

        return "redirect:/evento/" + evento.getId().toString();
    }
}
