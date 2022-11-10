package com.example.atmbanksystem.servlet;

import com.example.atmbanksystem.entity.Card;
import com.example.atmbanksystem.service.impl.CardServiceImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@WebServlet(name = "UserCardServlet")
public class CardServlet extends HttpServlet {

    private CardServiceImpl cardService;

    public void init() {
        cardService = new CardServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject resp = new JSONObject();
        response.setContentType("application/json");
        List<Card> cards = cardService.getAllCards();
        if (!cards.isEmpty()) {
            for (Card card : cards) {
                resp.put("Cards", card.toString());
                response.getWriter().println(resp);
            }
        } else {
            resp.put("Error", "No cards");
            response.getWriter().println(resp);
            response.setStatus(404);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject resp = new JSONObject();
        JSONParser parser = new JSONParser();
        response.setContentType("application/json");
        try (Reader reader = request.getReader()) {
            JSONObject userObject = (JSONObject) parser.parse(reader);
            Long pinNumberOfCard = (Long) userObject.get("pinNumberOfCard");
            Card card = cardService.findCardByPinNumber(Math.toIntExact(pinNumberOfCard));
            if (card != null) {
                resp.put("Card", card.toString());
                response.getWriter().println(resp);
            } else {
                resp.put("Error", "No card");
                response.getWriter().println(resp);
                response.setStatus(404);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
