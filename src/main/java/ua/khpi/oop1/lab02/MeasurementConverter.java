package ua.khpi.oop1.lab02;

public class MeasurementConverter {
    public static void main(String[] args) {
        double speedMeterPerSec = 15.5;
        int timeSeconds = 120;

        double speedKmPerHour = speedMeterPerSec * 3.6;
        double distanceMeters = speedMeterPerSec * timeSeconds;
        double distanceKm = distanceMeters / 1000.0;

        System.out.println("=== Транспортний датчик ===");
        System.out.printf("Початкова швидкість: %.2f м/с%n", speedMeterPerSec);
        System.out.printf("Час руху: %d с%n", timeSeconds);
        System.out.printf("Швидкість у км/год: %.2f км/год%n", speedKmPerHour);
        System.out.printf("Пройдений шлях: %.2f м (або %.3f км)%n", distanceMeters, distanceKm);
        System.out.println();

        System.out.println("=== Задача 2: Калібрування зі сталим кроком ===");
        runCalibrationTask((short) -500);
        runCalibrationTask((short) -1);
        runCalibrationTask((short) 0);
        runCalibrationTask((short) 1);
        runCalibrationTask((short) 800);
    }

    public static void runCalibrationTask(short r) {
        double k = 1.002;
        double b = -0.15;

        double t = (k * r) / 10.0 + b;
        short rPrime = (short) Math.round((t - b) * 10.0 / k);

        System.out.printf("r: %-4d | T: %7.4f C | r': %-4d | Різниця: %d%n", r, t, rPrime, (rPrime - r));
    }
}