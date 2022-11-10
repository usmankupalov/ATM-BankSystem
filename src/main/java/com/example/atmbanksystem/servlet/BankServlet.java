package com.example.atmbanksystem.servlet;

import com.example.atmbanksystem.entity.Bank;
import com.example.atmbanksystem.service.impl.BankServiceImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@WebServlet(name = "BankServlet")
public class BankServlet extends HttpServlet {

    private BankServiceImpl bankService;

    public void init() {
        bankService = new BankServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Bank> banks = bankService.getAllBanks();
        JSONObject resp = new JSONObject();
        response.setContentType("application/json");
        if (!banks.isEmpty()) {
            for (Bank bank : banks) {
                resp.put("bank_name", bank.getBankName());
                response.getWriter().println(resp);
            }
        } else {
            resp.put("Error", "No banks");
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
            String bankName = (String) userObject.get("bankName");
            Bank bank = bankService.findBankByName(bankName);
            if (bank != null) {
                resp.put("bank", bank.toString());
                response.getWriter().println(resp);
            } else {
                resp.put("Error", "There is no bank");
                response.getWriter().println(resp);
                response.setStatus(404);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
