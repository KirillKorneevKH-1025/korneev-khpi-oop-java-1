package ua.khpi.oop1.lab06;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Обов'язковий рівень (Варіант 2) ===");
        runMotionTest("Типовий рух", 5.0, 2.5, 8.0, true, 120.0, 25.0);
        runMotionTest("Рівномірний рух (a=0)", 6.0, 0.0, 5.0, true, 30.0, 6.0);
        runMotionTest("Нульовий час (t=0)", 5.0, 2.5, 0.0, true, 0.0, 5.0);
        runMotionTest("Від'ємний час (t<0)", 5.0, 2.5, -0.1, false, 0, 0);
        runMotionTest("Від'ємна швидкість", -5.0, 2.5, 8.0, false, 0, 0);
        runMotionTest("Помилка (NaN)", Double.NaN, 2.5, 8.0, false, 0, 0);

        System.out.println("\n=== Поглиблений рівень (Завдання B) ===");
        runBisectionTest("Типовий діапазон [1, 2]", 1.0, 2.0, true, Math.sqrt(2.0), 1e-6);
        runBisectionTest("Некоректний відрізок [2, 1]", 2.0, 1.0, false, 0, 0);
        runBisectionTest("Відрізок без зміни знаку [2, 3]", 2.0, 3.0, false, 0, 0);
    }

    // --- Обов'язковий рівень ---

    public static boolean isValidMotionInput(double initialVelocity, double acceleration, double time) {
        return Double.isFinite(initialVelocity) && initialVelocity >= 0.0
                && Double.isFinite(acceleration)
                && Double.isFinite(time) && time >= 0.0;
    }

    public static double calculateDisplacement(double initialVelocity, double acceleration, double time) {
        if (!isValidMotionInput(initialVelocity, acceleration, time)) {
            throw new IllegalArgumentException("Некоректні вхідні дані для руху");
        }
        double displacement = initialVelocity * time + 0.5 * acceleration * time * time;
        if (!Double.isFinite(displacement)) {
            throw new ArithmeticException("Переповнення під час обчислення переміщення");
        }
        return displacement;
    }

    public static double calculateFinalVelocity(double initialVelocity, double acceleration, double time) {
        if (!isValidMotionInput(initialVelocity, acceleration, time)) {
            throw new IllegalArgumentException("Некоректні вхідні дані для руху");
        }
        double finalVelocity = initialVelocity + acceleration * time;
        if (!Double.isFinite(finalVelocity)) {
            throw new ArithmeticException("Переповнення під час обчислення кінцевої швидкості");
        }
        return finalVelocity;
    }

    public static void runMotionTest(String name, double v0, double a, double t,
                                     boolean expectValid, double expDisplacement, double expVelocity) {
        System.out.printf("[%s] v0=%.1f, a=%.1f, t=%.1f%n", name, v0, a, t);
        try {
            double actualDisplacement = calculateDisplacement(v0, a, t);
            double actualVelocity = calculateFinalVelocity(v0, a, t);

            boolean passed = Math.abs(actualDisplacement - expDisplacement) < 1e-6
                    && Math.abs(actualVelocity - expVelocity) < 1e-6;

            System.out.printf("  Фактично: s=%.3f, v=%.3f | Очікувано: s=%.3f, v=%.3f | Статус: %s%n",
                    actualDisplacement, actualVelocity, expDisplacement, expVelocity, passed ? "PASS" : "FAIL");
        } catch (IllegalArgumentException | ArithmeticException e) {
            boolean passed = !expectValid;
            System.out.printf("  Відхилено: %s | Очікувано: %s | Статус: %s%n",
                    e.getMessage(), expectValid ? "Успіх" : "Помилка", passed ? "PASS" : "FAIL");
        }
    }

    // --- Поглиблений рівень ---

    public static double f(double x) {
        return x * x - 2.0;
    }

    public static boolean isValidBisectionInterval(double left, double right) {
        if (!Double.isFinite(left) || !Double.isFinite(right) || left >= right) {
            return false;
        }
        // Корінь має бути між кінцями відрізка (знаки функції повинні відрізнятися)
        return f(left) * f(right) <= 0;
    }

    public static double findRootBisection(double left, double right) {
        if (!isValidBisectionInterval(left, right)) {
            throw new IllegalArgumentException("Некоректний відрізок для методу бісекції");
        }

        double tolerance = 1e-6;
        int maxSteps = 40;
        int step = 0;
        double currentLeft = left;
        double currentRight = right;
        double mid = currentLeft;

        while ((currentRight - currentLeft) > tolerance && step < maxSteps) {
            mid = currentLeft + (currentRight - currentLeft) / 2.0;
            if (f(mid) == 0.0) {
                break; // Точний корінь
            } else if (f(currentLeft) * f(mid) < 0) {
                currentRight = mid;
            } else {
                currentLeft = mid;
            }
            step++;
        }

        // Для детального трасування лише одного разу:
        if (left == 1.0 && right == 2.0) {
            System.out.printf("  [Трасування] Фінальний відрізок: [%.6f, %.6f]%n", currentLeft, currentRight);
            System.out.printf("  [Трасування] Ширина: %.8f, Кроків: %d%n", (currentRight - currentLeft), step);
        }

        return currentLeft + (currentRight - currentLeft) / 2.0;
    }

    public static void runBisectionTest(String name, double left, double right,
                                        boolean expectValid, double expRoot, double tolerance) {
        System.out.printf("[%s] Відрізок: [%.1f, %.1f]%n", name, left, right);
        try {
            double actualRoot = findRootBisection(left, right);
            boolean passed = Math.abs(actualRoot - expRoot) <= tolerance;
            System.out.printf("  Фактично: %.6f | Очікувано: %.6f | Статус: %s%n",
                    actualRoot, expRoot, passed ? "PASS" : "FAIL");
        } catch (IllegalArgumentException e) {
            boolean passed = !expectValid;
            System.out.printf("  Відхилено: %s | Очікувано: %s | Статус: %s%n",
                    e.getMessage(), expectValid ? "Успіх" : "Помилка", passed ? "PASS" : "FAIL");
        }
    }
}