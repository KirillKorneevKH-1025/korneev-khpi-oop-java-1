package ua.khpi.oop1.lab05;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Обов'язковий рівень (Варіант 2) ===");
        // 1. Типовий набір
        runSequenceTest("Типовий набір", new int[]{2, 4, 1, 5, 3}, true, 6, 20, 2);
        // 2. Мінімальний набір (порожня послідовність)
        runSequenceTest("Мінімальний набір", new int[]{}, true, 0, 1, 0);
        // 3. Граничний набір (рівно 20 елементів)
        runSequenceTest("Граничний набір (20 ел.)",
                new int[]{1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5},
                true, 30, 14400, 16);
        // 4. Помилковий набір (число поза межами 1-5)
        runSequenceTest("Помилка (значення 6 > 5)", new int[]{2, 6, 1}, false, 0, 0, 0);
        // 5. Помилковий набір (розмір понад 20 елементів)
        runSequenceTest("Помилка (розмір 21 > 20)",
                new int[]{1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1},
                false, 0, 0, 0);

        System.out.println("\n=== Поглиблений рівень (Завдання B) ===");
        runLuhnTest("Чинний код", 12345674, true, 30, true);
        runLuhnTest("Нечинний код", 12345670, true, 26, false);
        runLuhnTest("Менше діапазону", 9999999, false, 0, false);
        runLuhnTest("Більше діапазону", 100000000, false, 0, false);
    }

    // Метод обчислення обов'язкового рівня (повертає масив [sum, product, pairCount] або null при помилці)
    public static long[] processSequence(int[] values) {
        int n = values.length;
        if (n < 0 || n > 20) {
            return null;
        }

        long sum = 0;
        long product = 1;
        int pairCount = 0;
        int previousValue = -1;
        boolean hasEvenPosition = false;

        for (int i = 0; i < n; i++) {
            int value = values[i];
            if (value < 1 || value > 5) {
                return null;
            }

            int position = i + 1;
            if (position % 2 != 0) {
                sum += value;
            } else {
                product *= value;
                hasEvenPosition = true;
            }

            if (i > 0 && value > previousValue) {
                pairCount++;
            }
            previousValue = value;
        }

        if (!hasEvenPosition) {
            product = 1;
        }

        return new long[]{sum, product, pairCount};
    }

    // Автоматична звірка для обов'язкового рівня
    public static void runSequenceTest(String name, int[] values, boolean expectValid,
                                       long expSum, long expProd, int expPairs) {
        long[] res = processSequence(values);
        boolean passed;

        if (!expectValid) {
            passed = (res == null);
            System.out.printf("[%s] Вхід: n=%d -> Відхилено (Очікувано: помилка) | Статус: %s%n",
                    name, values.length, passed ? "PASS" : "FAIL");
        } else {
            passed = (res != null && res[0] == expSum && res[1] == expProd && res[2] == expPairs);
            System.out.printf("[%s] Очікувано: sum=%d, prod=%d, pairs=%d | Фактично: sum=%d, prod=%d, pairs=%d | Статус: %s%n",
                    name, expSum, expProd, expPairs, res[0], res[1], res[2], passed ? "PASS" : "FAIL");
        }
    }

    // Метод обчислення поглибленого рівня (повертає [sum, isValid(1/0)] або null при помилці)
    public static int[] checkLuhn(int code) {
        if (code < 10_000_000 || code > 99_999_999) {
            return null;
        }

        int temp = code;
        int sum = 0;
        boolean isSecond = false;

        while (temp > 0) {
            int digit = temp % 10;
            if (isSecond) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            isSecond = !isSecond;
            temp /= 10;
        }

        int isValid = (sum % 10 == 0) ? 1 : 0;
        return new int[]{sum, isValid};
    }

    // Автоматична звірка для поглибленого рівня
    public static void runLuhnTest(String name, int code, boolean expectInRange, int expSum, boolean expValid) {
        int[] res = checkLuhn(code);
        boolean passed;

        if (!expectInRange) {
            passed = (res == null);
            System.out.printf("[%s] Код: %d -> Відхилено за діапазоном | Статус: %s%n",
                    name, code, passed ? "PASS" : "FAIL");
        } else {
            boolean actualValid = (res[1] == 1);
            passed = (res[0] == expSum && actualValid == expValid);
            System.out.printf("[%s] Код: %d -> Очікувано: sum=%d, valid=%b | Фактично: sum=%d, valid=%b | Статус: %s%n",
                    name, code, expSum, expValid, res[0], actualValid, passed ? "PASS" : "FAIL");
        }
    }
}