package com.eventoapp.eventoapp.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.models.Evento;
import com.eventoapp.eventoapp.repositories.ConvidadoRepository;
import com.eventoapp.eventoapp.repositories.EventoRepository;

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
    public String form(Evento evento) {

        er.save(evento);

        return "redirect:/";
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

        return mv;
    }

    @RequestMapping(value = "/evento/{id}", method = RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("id") UUID id, Convidado convidado) {
        Optional<Evento> evento = er.findById(id);

        convidado.setEvento(evento.get());
        cr.save(convidado);

        return "redirect:/evento/" + id.toString();
    }
}
