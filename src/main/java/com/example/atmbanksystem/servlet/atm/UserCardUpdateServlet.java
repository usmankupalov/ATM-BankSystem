package com.example.atmbanksystem.servlet.atm;

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

@WebServlet(name = "UserCardUpdateServlet")
public class UserCardUpdateServlet extends HttpServlet {

    private CardServiceImpl cardService;

    public void init() {
        cardService = new CardServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject resp = new JSONObject();
        JSONParser parser = new JSONParser();
        response.setContentType("application/json");
        try (Reader reader = request.getReader()) {
            JSONObject userObject = (JSONObject) parser.parse(reader);
            Long pinNumberOfCard = (Long) userObject.get("pinNumberOfCard");
            Long currentPassword = (Long) userObject.get("currentPassword");
            Long newPassword = (Long) userObject.get("newPassword");
            Card card = cardService.findCardByPinNumberAndPassword(Math.toIntExact(pinNumberOfCard), Math.toIntExact(currentPassword));
            if (card != null) {
                cardService.updateCardPassword(card.getCardId(), Math.toIntExact(newPassword));
                resp.put("Answer", "Password updated");
                response.getWriter().println(resp);
            } else {
                resp.put("Error", "Wrong input pinNumber/password");
                response.getWriter().println(resp);
                response.setStatus(400);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
