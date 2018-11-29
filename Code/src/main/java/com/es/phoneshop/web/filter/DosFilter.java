package com.es.phoneshop.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.es.phoneshop.model.helping.Constants.*;

public class DosFilter implements Filter {

    private boolean accessAllowed = true;
    private Timer timer, errorTimer;
    private TimerTask task, errorTask;
    private String address;

    private Integer waitInterval;
    private Integer interval;
    private Integer maxRequestCount;


    private Map requestCountMap = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            waitInterval = Integer.parseInt(filterConfig.getInitParameter(ERROR_TIMER_PARAM_NAME));
            interval = Integer.parseInt(filterConfig.getInitParameter(TIMER_PARAM_NAME));
            maxRequestCount = Integer.parseInt(filterConfig.getInitParameter(MAX_REQUEST_PARAM_NAME));
            if(waitInterval == null || interval == null || maxRequestCount == null) {
                throw new NoSuchFieldException();
            }
        }
        catch (NumberFormatException | NoSuchFieldException e) {
            waitInterval = DEFAULT_WAIT_INTERVAL_IN_MILLISECONDS;
            interval = DEFAULT_INTERVAL_IN_MILLISECONDS;
            maxRequestCount = DEFAULT_MAX_REQUEST_COUNT;
        }
        update();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        servletRequest.setCharacterEncoding(ENCODING);
        servletRequest.setAttribute(WAIT_ATTRIBUTE_NAME, waitInterval / MILLISECONDS_IN_SECOND);

        if (!accessAllowed) {
            destroyTimer(errorTimer,errorTask);
            updateErrorTimer(servletResponse);
            ((HttpServletResponse) servletResponse).sendError(TOO_MANY_REQUESTS_ERROR);
        } else {
            address = servletRequest.getRemoteAddr();
            AtomicInteger count = (AtomicInteger) requestCountMap.get(address);
            if (count == null) {
                count = new AtomicInteger(1);
            } else {
                count.incrementAndGet();
            }

            requestCountMap.put(address, count);

            if (count.intValue() > maxRequestCount) {

                accessAllowed = false;

                updateErrorTimer(servletResponse);

                destroyTimer(timer,task);

                ((HttpServletResponse) servletResponse).sendError(TOO_MANY_REQUESTS_ERROR);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }

        }
    }

    @Override
    public void destroy() {

    }

    private void update() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (address != null) {
                    requestCountMap.remove(address);
                }
            }
        };
        timer.scheduleAtFixedRate(task, interval, interval);
    }

    private void updateErrorTimer(ServletResponse response) {
        errorTimer = new Timer();
        errorTask = new TimerTask() {
            @Override
            public void run() {
                accessAllowed = true;
                if (address != null) {
                    requestCountMap.remove(address);
                }
                destroyTimer(errorTimer,errorTask);
                update();
            }
        };
        errorTimer.scheduleAtFixedRate(errorTask, waitInterval, waitInterval);
    }

    private void destroyTimer(Timer timer,TimerTask timerTask) {
        timer.cancel();
        timerTask.cancel();
        timer = null;
        timerTask = null;
    }
}