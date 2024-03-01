package com.vix.monedas.interfaces;

import com.vix.monedas.data.Moneda;

public interface LoadMonedasTask {
    void onTaskCompleted(Moneda moneda);
}