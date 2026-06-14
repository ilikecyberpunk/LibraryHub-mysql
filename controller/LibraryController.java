package controller;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import dao.impl.BookDAOImpl;
import dao.impl.LoanDAOImpl;
import dao.impl.MemberDAOImpl;
import model.Book;
import model.Member;
import java.io.*;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class LibraryController {

    static BookDAOImpl bookDAO = new BookDAOImpl();
    static MemberDAOImpl memberDAO = new MemberDAOImpl();
    static LoanDAOImpl loanDAO = new LoanDAOImpl();

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // 도서 목록
        server.createContext("/books", exchange -> {
            addCors(exchange);
            if ("OPTIONS".equals(exchange.getRequestMethod())) { 
                exchange.sendResponseHeaders(204, -1); 
                return; 
            }
            
            ArrayList<Book> books = bookDAO.findAll();
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < books.size(); i++) {
                Book b = books.get(i);
                sb.append("{\"bookId\":").append(b.getBookId())
                  .append(",\"title\":\"").append(escape(b.getTitle())).append("\"")
                  .append(",\"author\":\"").append(escape(b.getAuthor())).append("\"")
                  .append(",\"isAvailable\":").append(b.isAvailable()).append("}");
                if (i < books.size() - 1) sb.append(",");
            }
            sb.append("]");
            sendResponse(exchange, sb.toString());
        });

        // 회원 목록
        server.createContext("/members", exchange -> {
            addCors(exchange);
            if ("OPTIONS".equals(exchange.getRequestMethod())) { exchange.sendResponseHeaders(204, -1); return; }
            ArrayList<Member> members = memberDAO.findAll();
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < members.size(); i++) {
                Member m = members.get(i);
                sb.append("{\"memberId\":").append(m.getMemberId())
                  .append(",\"name\":\"").append(escape(m.getName())).append("\"")
                  .append(",\"phone\":\"").append(escape(m.getPhone())).append("\"}");
                if (i < members.size() - 1) sb.append(",");
            }
            sb.append("]");
            sendResponse(exchange, sb.toString());
        });

        // 대출 등록
        server.createContext("/loan", exchange -> {
            addCors(exchange);
            if ("OPTIONS".equals(exchange.getRequestMethod())) { exchange.sendResponseHeaders(204, -1); return; }
            if ("POST".equals(exchange.getRequestMethod())) {
                String body = new String(exchange.getRequestBody().readAllBytes());
                String[] params = body.split("&");
                int bookId   = Integer.parseInt(params[0].split("=")[1]);
                int memberId = Integer.parseInt(params[1].split("=")[1]);
                loanDAO.registerLoan(bookId, memberId);
                sendResponse(exchange, "{\"result\":\"대출 등록 완료\"}");
            }
        });

        // 반납
        server.createContext("/return", exchange -> {
            addCors(exchange);
            if ("OPTIONS".equals(exchange.getRequestMethod())) { exchange.sendResponseHeaders(204, -1); return; }
            if ("POST".equals(exchange.getRequestMethod())) {
                String body = new String(exchange.getRequestBody().readAllBytes());
                String[] params = body.split("&");
                int loanId = Integer.parseInt(params[0].split("=")[1]);
                int bookId = Integer.parseInt(params[1].split("=")[1]);
                loanDAO.returnBook(loanId, bookId);
                sendResponse(exchange, "{\"result\":\"반납 완료\"}");
            }
        });

        server.start();
        System.out.println("서버 시작: http://localhost:8080");
        // 대출 목록 조회 - main() 안에 추가
server.createContext("/loans", exchange -> {
    addCors(exchange);
    if ("OPTIONS".equals(exchange.getRequestMethod())) { exchange.sendResponseHeaders(204, -1); return; }
    Connection con = util.DBConnection.getConnection();
    PreparedStatement pstmt = null;
    java.sql.ResultSet rs = null;
    String sql = "SELECT L.LOAN_ID, B.TITLE, M.NAME, L.LOAN_DATE " +
                 "FROM LOAN L " +
                 "JOIN BOOK B ON L.BOOK_ID = B.BOOK_ID " +
                 "JOIN MEMBER M ON L.MEMBER_ID = M.MEMBER_ID " +
                 "WHERE L.RETURN_DATE IS NULL " +
                 "ORDER BY L.LOAN_ID DESC";
    StringBuilder sb = new StringBuilder("[");
    try {
        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
        boolean first = true;
        while (rs.next()) {
            if (!first) sb.append(",");
            sb.append("{\"loanId\":").append(rs.getInt("LOAN_ID"))
              .append(",\"title\":\"").append(escape(rs.getString("TITLE"))).append("\"")
              .append(",\"name\":\"").append(escape(rs.getString("NAME"))).append("\"")
              .append(",\"loanDate\":\"").append(rs.getDate("LOAN_DATE")).append("\"}");
            first = false;
        }
    } catch (Exception e) { System.out.println(e); }
    finally {
        try { if(rs!=null)rs.close(); if(pstmt!=null)pstmt.close(); if(con!=null)con.close(); } catch(Exception e){}
    }
    sb.append("]");
    sendResponse(exchange, sb.toString());
    });
    }

    static void addCors(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    static void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }

    static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    
}