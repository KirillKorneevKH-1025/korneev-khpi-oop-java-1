package ua.khpi.oop1.lab04;

public class DecisionController {

    public static void main(String[] args) {
        System.out.println("=== Обов'язковий рівень (Варіант 2: Рівень резервуара) ===");
        // Перевірка граничних та тестових значень
        evaluateTankLevel(Double.NaN, false);    // Некоректні дані (NaN)
        evaluateTankLevel(150.0, false);         // Поза діапазоном (>100)
        evaluateTankLevel(-10.0, false);         // Поза діапазоном (<0)
        evaluateTankLevel(50.0, true);           // Технічна помилка датчика
        evaluateTankLevel(10.0, false);          // low (внутрішня точка)
        evaluateTankLevel(19.9, false);          // low (перед межею)
        evaluateTankLevel(20.0, false);          // normal (межа)
        evaluateTankLevel(50.0, false);          // normal (внутрішня точка)
        evaluateTankLevel(79.9, false);          // normal (перед межею)
        evaluateTankLevel(80.0, false);          // high (межа)
        evaluateTankLevel(100.0, false);         // high (верхня допустима межа)

        System.out.println("\n=== Поглиблений рівень (Завдання В: Мертва зона) ===");
        // Тести за методичкою
        evaluateControl(5, 0);     // очікувано: (0, 0)
        evaluateControl(-10, 0);   // очікувано: (-5, -5)
        evaluateControl(100, 0);   // очікувано: (20, 8)
        evaluateControl(100, 16);  // очікувано: (20, 20)
    }

    // --- Обов'язковий рівень ---
    public static void evaluateTankLevel(double level, boolean sensorFault) {
        String state;
        String action;

        if (!Double.isFinite(level) || level < 0.0 || level > 100.0) {
            state = "invalid";
            action = "відхилити значення";
        } else if (sensorFault) {
            state = "technical-error";
            action = "перевірити датчик";
        } else if (level < 20.0) {
            state = "low";
            action = "увімкнути насос";
        } else if (level < 80.0) {
            state = "normal";
            action = "залишити без змін";
        } else {
            state = "high";
            action = "вимкнути";
        }

        System.out.printf("Вхід: level = %5.1f, sensorFault = %5b | Стан: %-15s | Дія: %s%n",
                level, sensorFault, state, action);
    }

    // --- Поглиблений рівень (Завдання В) ---
    public static void evaluateControl(int x, int previous) {
        if (x < -100 || x > 100 || previous < -20 || previous > 20) {
            System.out.println("Помилка: значення поза допустимим діапазоном.");
            return;
        }

        // 1. Мертва зона [-5, 5] та обчислення цільового значення
        int target;
        if (x >= -5 && x <= 5) {
            target = 0;
        } else if (x > 5) {
            target = x - 5;
        } else {
            target = x + 5;
        }

        // Насичення [-20, 20]
        if (target > 20) {
            target = 20;
        } else if (target < -20) {
            target = -20;
        }

        // 2. Обмеження зміни (не більше 8 від previous)
        int minAllowed = previous - 8;
        int maxAllowed = previous + 8;

        // Фізичне обмеження мін/макс інтервалом [-20, 20]
        if (minAllowed < -20) minAllowed = -20;
        if (maxAllowed > 20) maxAllowed = 20;

        int result;
        if (target > maxAllowed) {
            result = maxAllowed;
        } else if (target < minAllowed) {
            result = minAllowed;
        } else {
            result = target;
        }

        System.out.printf("Вхід: x = %4d, previous = %3d | Ціль: %3d | Результат: %3d%n",
                x, previous, target, result);
    }
}