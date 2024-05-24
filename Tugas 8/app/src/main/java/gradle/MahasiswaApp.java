package gradle;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import gradle.models.Mahasiswa;

public class MahasiswaApp {
    private List<Mahasiswa> dataMhs;

    public MahasiswaApp() {
        this.dataMhs = Loader.csvMhsLoad();
    }

    public List<Mahasiswa> sort(int sortBy) {
        if (sortBy == 1) {
            return dataMhs.stream()
                    .sorted(Comparator.comparing(Mahasiswa::getNama))
                    .toList();
        } else if (sortBy == 2) {
            return dataMhs.stream()
                    .sorted(Comparator.comparing(Mahasiswa::getIpk).reversed())
                    .toList();
        } else {
            return dataMhs.stream()
                    .sorted((m1, m2) -> m1.getNim().compareTo(m2.getNim()))
                    .toList();
        }
    }

    public double averageIpk() {
        return dataMhs.stream().mapToDouble(Mahasiswa::getIpk).average().orElse(0.0);
    }

    public List<Mahasiswa> filterCumlaude() {
        return dataMhs.stream().filter(mhs -> mhs.getIpk() >= 3.5).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        MahasiswaApp app = new MahasiswaApp();

        // Sorting by name
        System.out.println("Sorted by name:");
        List<Mahasiswa> sortedByName = app.sort(1);
        sortedByName.forEach(System.out::println);

        // Sorting by IPK
        System.out.println("\nSorted by IPK (descending):");
        List<Mahasiswa> sortedByIpk = app.sort(2);
        sortedByIpk.forEach(System.out::println);

        // Sorting by NIM
        System.out.println("\nSorted by NIM:");
        List<Mahasiswa> sortedByNim = app.sort(3);
        sortedByNim.forEach(System.out::println);

        // Average IPK
        System.out.println("\nAverage IPK: " + app.averageIpk());

        // Filtering cum laude
        System.out.println("\nCum Laude Students:");
        List<Mahasiswa> cumLaude = app.filterCumlaude();
        cumLaude.forEach(System.out::println);
    }
}
