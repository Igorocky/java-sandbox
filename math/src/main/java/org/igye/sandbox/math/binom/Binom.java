package org.igye.sandbox.math.binom;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jscience.mathematics.number.Rational;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Binom {
    public static void main(String[] args) {
        Binom binom = new Binom();
        binom.run();
    }

    private void run() {
        String binom1 = polToString(binomV1(2L));
        String binom2 = polToString(binomV2(2L));
        assert binom1.equals(binom2);
        System.out.println(binom1);
        System.out.println(binom2);
    }

    private List<Pair<Rational, List<Long>>> binomV1(long n) {
        return pow(
            List.of(
                Pair.of(Rational.valueOf(1, 1), List.of(1L, 0L)),
                Pair.of(Rational.valueOf(1, 1), List.of(0L, 1L))
            ),
            n
        );
    }

    private List<Pair<Rational, List<Long>>> binomV2(long n) {
        List<Pair<Rational, List<Long>>> res = new ArrayList<>();
        for (long k = 0; k <= n; k++) {
            Rational left = Rational.valueOf(factorial(n), factorial(k) * factorial(n - k));
            List<Long> right = List.of(n - k, k);
            res.add(Pair.of(left, right));
        }
        return sort(res);
    }

    private List<Pair<Rational, List<Long>>> pow(List<Pair<Rational, List<Long>>> pol, long exp) {
        List<Pair<Rational, List<Long>>> res = pol;
        while (exp > 1) {
            res = mul(res, pol);
            exp--;
        }
        return res;
    }

    private String polToString(List<Pair<Rational, List<Long>>> pol) {
        return pol.stream()
            .map(e -> String.format("(%s,%s)", e.getLeft(), StringUtils.join(e.getRight(), ",")))
            .collect(Collectors.joining(" "));
    }

    private List<Pair<Rational, List<Long>>> mul(
        List<Pair<Rational, List<Long>>> pol1,
        List<Pair<Rational, List<Long>>> pol2
    ) {
        List<Pair<Rational, List<Long>>> res = new ArrayList<>();
        for (Pair<Rational, List<Long>> a : pol1) {
            for (Pair<Rational, List<Long>> b : pol2) {
                assert a.getRight().size() == b.getRight().size();
                res.add(Pair.of(
                    a.getLeft().times(b.getLeft()),
                    Stream.iterate(0, i -> i + 1)
                        .limit(a.getRight().size())
                        .map(i -> a.getRight().get(i) + b.getRight().get(i))
                        .toList()
                ));
            }
        }
        return sort(
            res.stream().collect(Collectors.toMap(
                e -> StringUtils.join(e.getRight(), ","),
                Function.identity(),
                (e1, e2) -> Pair.of(e1.getLeft().plus(e2.getLeft()), e1.getRight())
            )).values().stream().toList()
        );
    }

    private List<Pair<Rational, List<Long>>> sort(List<Pair<Rational, List<Long>>> pol) {
        return pol.stream().sorted(Comparator.comparing(Pair::getRight, this::compare)).toList();
    }

    private int compare(List<Long> a, List<Long> b) {
        assert a.size() == b.size();
        for (int i = a.size() - 1; i >= 0; i--) {
            Long ai = a.get(i);
            Long bi = b.get(i);
            if (ai < bi) {
                return -1;
            } else if (ai > bi) {
                return 1;
            }
        }
        return 0;
    }

    private long factorial(long n) {
        return n == 0 || n == 1 ? 1 : n * factorial(n - 1);
    }
}
