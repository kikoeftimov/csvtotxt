package com.example.converting.demo.service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface DownloadService {

    void getLogFile(HttpSession session, HttpServletResponse response) throws Exception;
}
