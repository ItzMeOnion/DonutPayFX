
package com.donutpayfx;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentStore {

    private final Map<String, Long> totals = new HashMap<>();

    public void add(Payment payment) {
        totals.merge(
            payment.player(),
            payment.amount(),
            Long::sum
        );
    }

    public long total(String player) {
        return totals.getOrDefault(player, 0L);
    }

    public List<Map.Entry<String, Long>> topPayers() {
        return totals.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue()
                .reversed())
            .limit(10)
            .toList();
    }
}
