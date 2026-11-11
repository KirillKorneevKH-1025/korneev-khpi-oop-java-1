package ua.khpi.oop1.lab05;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Обов'язковий рівень (Варіант 2) ===");
        // 1. Типовий набір
        processSequence(new int[]{2, 4, 1, 5, 3});
        // 2. Мінімальний набір (порожня послідовність)
        processSequence(new int[]{});
        // 3. Граничний набір (рівно 20 елементів)
        processSequence(new int[]{1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5});
        // 4. Помилковий набір (число поза межами 1-5)
        processSequence(new int[]{2, 6, 1});

        System.out.println("\n=== Поглиблений рівень (Завдання B) ===");
        checkLuhn(12345674); // Чинний код (сума 30)
        checkLuhn(12345670); // Нечинний код
        checkLuhn(9999999);  // Відхилення за діапазоном (менше 10 000 000)
        checkLuhn(100000000); // Відхилення за діапазоном (більше 99 999 999)
    }

    // Метод обов'язкового рівня
    public static void processSequence(int[] values) {
        int n = values.length;
        if (n < 0 || n > 20) {
            System.out.println("Помилка: кількість має бути від 0 до 20.");
            return;
        }

        long sum = 0;
        long product = 1;
        int pairCount = 0;
        int previousValue = -1;

        boolean hasEvenPosition = false;

        for (int i = 0; i < n; i++) {
            int value = values[i];
            if (value < 1 || value > 5) {
                System.out.println("Помилка: значення " + value + " поза межами 1-5.");
                return;
            }

            int position = i + 1; // Позиції рахуємо з 1
            if (position % 2 != 0) {
                sum += value; // Непарні позиції
            } else {
                product *= value; // Парні позиції
                hasEvenPosition = true;
            }

            // Перевірка сусідніх пар, де друге більше за перше
            if (i > 0 && value > previousValue) {
                pairCount++;
            }
            previousValue = value;
        }

        // Якщо парних позицій не було взагалі, добуток має залишитися 1 (нейтральне)
        if (!hasEvenPosition) {
            product = 1;
        }

        System.out.printf("Вхід: кількість = %d%n", n);
        System.out.printf("Сума (непарні позиції): %d | Добуток (парні позиції): %d%n", sum, product);
        System.out.printf("Кількість зростаючих пар: %d%n", pairCount);
        System.out.println("--------------------------------------------------");
    }

    // Метод поглибленого рівня
    public static void checkLuhn(int code) {
        if (code < 10_000_000 || code > 99_999_999) {
            System.out.println("Помилка: код " + code + " поза межами 10 000 000 - 99 999 999.");
            return;
        }

        int temp = code;
        int sum = 0;
        boolean isSecond = false; // Рухаємось справа наліво: остання цифра НЕ подвоюється

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

        boolean isValid = (sum % 10 == 0);
        System.out.printf("Код: %d | Сума цифр: %d | Чинний: %b%n", code, sum, isValid);
    }
}