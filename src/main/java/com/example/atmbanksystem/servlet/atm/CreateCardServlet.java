package com.example.atmbanksystem.servlet.atm;

import com.example.atmbanksystem.entity.Bank;
import com.example.atmbanksystem.entity.Card;
import com.example.atmbanksystem.entity.CardType;
import com.example.atmbanksystem.entity.User;
import com.example.atmbanksystem.service.impl.CardServiceImpl;
import com.example.atmbanksystem.service.impl.BankServiceImpl;
import com.example.atmbanksystem.service.impl.CardTypeServiceImpl;
import com.example.atmbanksystem.service.impl.UserServiceImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CreateCardServlet")
public class CreateCardServlet extends HttpServlet {

    private UserServiceImpl userService;
    private CardServiceImpl cardService;
    private BankServiceImpl bankService;
    private CardTypeServiceImpl cardTypeService;

    public void init() {
        userService = new UserServiceImpl();
        cardService = new CardServiceImpl();
        bankService = new BankServiceImpl();
        cardTypeService = new CardTypeServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONParser parser = new JSONParser();
        JSONObject resp = new JSONObject();
        response.setContentType("application/json");
        try (Reader reader = request.getReader()) {
            JSONObject userObject = (JSONObject) parser.parse(reader);
            String firstname = (String) userObject.get("firstname");
            String lastname = (String) userObject.get("lastname");
            Long pinNumberOfPassport = (Long) userObject.get("pinNumberOfPassport");
            String bankName = (String) userObject.get("bankName");
            String cardType = (String) userObject.get("cardType");
            Long passwordOfYourCard = (Long) userObject.get("passwordOfYourCard");
            if (!checkPinNumberOfUser(Math.toIntExact(pinNumberOfPassport))) {
                Bank bank = bankService.findBankByName(bankName);
                CardType card = cardTypeService.getCardTypeByName(cardType);
                if (bank != null && card != null) {
                    User user = new User(firstname, lastname, Math.toIntExact(pinNumberOfPassport));
                    userService.createUser(user);

                    Date date = new Date();
                    date.getTime();
                    Card card1 = new Card();
                    card1.setPasswordOfCard(Math.toIntExact(passwordOfYourCard));
                    card1.setPinNumber(Math.toIntExact(pinNumberOfPassport));
                    card1.setDateCreated(date);
                    card1.setCardType(card);
                    card1.setUser(user);
                    card1.setBank(bank);
                    cardService.createCard(card1);

                    resp.put("Answer", user.getFirstname() + " created card in bank " + bank.getBankName());
                    response.getWriter().println(resp);
                } else {
                    resp.put("Error", "Wrong bank or card type input");
                    response.getWriter().println(resp);
                    response.setStatus(400);
                }
            } else {
                resp.put("Error", "pin number is wrong or already exists");
                response.getWriter().println(resp);
                response.setStatus(400);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPinNumberOfUser(Integer pinNumberOfUser) {
        List<User> users = userService.getAllUsers();
        boolean result = false;
        for (User user : users) {
            if (user.getPinNumberOfPassport().equals(pinNumberOfUser)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
