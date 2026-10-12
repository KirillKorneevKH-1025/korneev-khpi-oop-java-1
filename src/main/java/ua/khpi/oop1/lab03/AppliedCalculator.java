package ua.khpi.oop1.lab03;

public class AppliedCalculator {

    public static void main(String[] args) {
        System.out.println("=== Лабораторна робота №3: Варіант 2 ===");

        System.out.println("\n--- Обов'язковий рівень ---");
        System.out.println("1. Типовий набір:");
        calculateEnergy(2.0, 5.0, 10.0);
        System.out.println("2. Мінімальний набір:");
        calculateEnergy(2.0, 0.0, 0.0);
        System.out.println("3. Максимальний набір:");
        calculateEnergy(1000.0, 150.0, 5000.0);
        System.out.println("4. Спеціальний набір:");
        calculateEnergy(5.0, -10.0, 15.0);

        System.out.println("\n--- Поглиблений рівень ---");
        System.out.println("1. Перевірка 0x81 ліворуч на 1 та k=0:");
        rotateByte(0x81, 1);
        rotateByte(0x81, 0);
        System.out.println("\n2. Перевірка відхилення некоректних даних:");
        rotateByte(-5, 2);
        rotateByte(256, 2);
        rotateByte(100, -1);
        rotateByte(100, 8);
    }

    public static void calculateEnergy(double massKg, double velocityMps, double heightMeters) {
        final double GRAVITY_MPS2 = 9.81;

        double velocitySquared = Math.pow(velocityMps, 2);
        double kineticEnergyJoules = 0.5 * massKg * velocitySquared;
        double potentialEnergyJoules = massKg * GRAVITY_MPS2 * heightMeters;

        double totalEnergyJoules = kineticEnergyJoules + potentialEnergyJoules;

        double controlMassKg = 0.0;
        if (heightMeters != 0) {
            controlMassKg = potentialEnergyJoules / (GRAVITY_MPS2 * heightMeters);
        } else if (velocityMps != 0) {
            controlMassKg = (2 * kineticEnergyJoules) / velocitySquared;
        } else {
            controlMassKg = massKg;
        }

        System.out.printf("Вхідні дані: m = %.2f кг, v = %.2f м/с, h = %.2f м%n", massKg, velocityMps, heightMeters);
        System.out.printf("Кінетична енергія (E_k):  %.3f Дж%n", kineticEnergyJoules);
        System.out.printf("Потенціальна енергія (E_p): %.3f Дж%n", potentialEnergyJoules);
        System.out.printf("Повна енергія (E): %.3f Дж%n", totalEnergyJoules);
        System.out.printf("Відновлена маса (контроль): %.2f кг | Похибка: %.6f%n",
                controlMassKg, Math.abs(controlMassKg - massKg));
        System.out.println("--------------------------------------------------");
    }

    public static void rotateByte(int value, int k) {
        if (value < 0 || value > 255) {
            System.out.println("Помилка: значення має бути в межах 0-255. Отримано: " + value);
            return;
        }
        if (k < 0 || k > 7) {
            System.out.println("Помилка: зсув k має бути в межах 0-7. Отримано: " + k);
            return;
        }

        int leftShifted = ((value << k) | (value >>> (8 - k))) & 0xFF;
        int rightShifted = ((value >>> k) | (value << (8 - k))) & 0xFF;

        System.out.printf("Вхід: 0x%02X (k=%d) | Ліворуч: 0x%02X | Праворуч: 0x%02X%n",
                value, k, leftShifted, rightShifted);
    }
}