package com.example.atmbanksystem.servlet.atm;

import com.example.atmbanksystem.entity.Card;
import com.example.atmbanksystem.entity.Transaction;
import com.example.atmbanksystem.service.impl.CardServiceImpl;
import com.example.atmbanksystem.service.impl.TransactionServiceImpl;
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

@WebServlet(name = "UserCardTransactionServlet")
public class UserCardTransactionServlet extends HttpServlet {

    private TransactionServiceImpl transactionService;
    private CardServiceImpl cardService;

    public void init() {
        transactionService = new TransactionServiceImpl();
        cardService = new CardServiceImpl();
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
            Card card = cardService.findCardByPinNumberAndPassword(Math.toIntExact(pinNumberOfCard), Math.toIntExact(password));
            if (card != null) {
                List<Transaction> transaction = transactionService.getAllTransactionOfOneCard(card.getCardId());
                if (!transaction.isEmpty()) {
                    resp.put("Answer", transaction.toString());
                    response.getWriter().println(resp);
                }
            } else {
                resp.put("Error", "Wrong input password/pinNumberOfCard");
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
            Long pinNumberOfCardFromTransferMoney = (Long) userObject.get("pinNumberOfCardFromTransferMoney");
            Long pinNumberOfCardToTransferMoney = (Long) userObject.get("pinNumberOfCardToTransferMoney");
            Long amountMoney = (Long) userObject.get("amountMoney");
            String memo = (String) userObject.get("memo");
            Card fromCard  = cardService.findCardByPinNumber(Math.toIntExact(pinNumberOfCardFromTransferMoney));
            Card toCard = cardService.findCardByPinNumber(Math.toIntExact(pinNumberOfCardToTransferMoney));
            if (fromCard != null && toCard != null) {
                if (amountMoney > 0.0) {
                    if (fromCard.getAmountMoney() > amountMoney) {
                        double overallMoneyCardTo = toCard.getAmountMoney() + amountMoney;
                        toCard.setAmountMoney(overallMoneyCardTo);
                        cardService.updateCardSumMoney(toCard.getCardId(), toCard.getAmountMoney());

                        double overallMoneyCardFrom = fromCard.getAmountMoney() - amountMoney;
                        fromCard.setAmountMoney(overallMoneyCardFrom);
                        cardService.updateCardSumMoney( fromCard.getCardId(),  fromCard.getAmountMoney());

                        Date date = new Date();
                        date.getTime();
                        Transaction transaction = new Transaction();
                        transaction.setAmountMoney(amountMoney);
                        transaction.setTimeOfTransaction(date);
                        transaction.setMemo(memo);
                        transaction.setFromCard(fromCard);
                        transaction.setToCard(toCard);
                        transactionService.saveTransaction(transaction);
                        resp.put("Answer", "Transaction was successful!");
                        response.getWriter().println(resp);
                    }
                }
                else {
                        resp.put("Error", "Money should be greater than zero");
                        response.getWriter().println(resp);
                        response.setStatus(400);
                }
            } else {
                resp.put("Error", "Wrong pin number");
                response.getWriter().println(resp);
                response.setStatus(404);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
