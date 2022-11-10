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

@WebServlet(name = "UserCardServlet")
public class UserCardServlet extends HttpServlet {

    private CardServiceImpl cardService;

    public void init() {
        cardService = new CardServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject resp = new JSONObject();
        JSONParser parser = new JSONParser();
        response.setContentType("application/json");
        try (Reader reader = request.getReader()) {
            JSONObject userObject = (JSONObject) parser.parse(reader);
            Long pinNumberOfCard = (Long) userObject.get("pinNumberOfCard");
            Long passwordOfCard = (Long) userObject.get("passwordOfCard");
            Card card = cardService.findCardByPinNumberAndPassword(Math.toIntExact(pinNumberOfCard), Math.toIntExact(passwordOfCard));
            if (card != null) {
                resp.put("Money in card", card.getAmountMoney());
                response.getWriter().println(resp);
            } else {
                resp.put("Error", "Wrong pin number/password");
                response.getWriter().println(resp);
                response.setStatus(404);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject resp = new JSONObject();
        JSONParser parser = new JSONParser();
        response.setContentType("application/json");
        try (Reader reader = request.getReader()) {
            JSONObject userObject = (JSONObject) parser.parse(reader);
            Long pinNumberOfCard = (Long) userObject.get("pinNumberOfCard");
            Long password = (Long) userObject.get("passwordOfCard");
            Long moneyTobeAdded = (Long) userObject.get("sumOfMoney");
            Card card = cardService.findCardByPinNumberAndPassword(Math.toIntExact(pinNumberOfCard), Math.toIntExact(password));
            if (card != null && moneyTobeAdded > 0.9) {
                double overallMoney = card.getAmountMoney() + moneyTobeAdded;
                card.setAmountMoney(overallMoney);
                cardService.updateCardSumMoney(card.getCardId(), card.getAmountMoney());
                resp.put("Answer", "Money added");
                response.getWriter().println(resp);
            } else {
                resp.put("Error", "Wrong input money/password");
                response.getWriter().println(resp);
                response.setStatus(400);
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
            Long password = (Long) userObject.get("passwordOfCard");
            Long moneyWithdrawn = (Long) userObject.get("moneyWithdrawn");
            Card card = cardService.findCardByPinNumberAndPassword(Math.toIntExact(pinNumberOfCard), Math.toIntExact(password));
            if (card != null ) {
                if (card.getAmountMoney() > moneyWithdrawn && moneyWithdrawn > 0) {
                    double overallMoney = card.getAmountMoney() - moneyWithdrawn;
                    card.setAmountMoney(overallMoney);
                    cardService.updateCardSumMoney(card.getCardId(), card.getAmountMoney());
                    resp.put("Answer", "Money withdrawn");
                    response.getWriter().println(resp);
                } else {
                    resp.put("Error", "Don't have enough money in card");
                    response.getWriter().println(resp);
                    response.setStatus(400);
                }
            } else {
                resp.put("Error", "Wrong pin number/password");
                response.getWriter().println(resp);
                response.setStatus(400);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
