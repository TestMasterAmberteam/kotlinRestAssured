package pl.amberteam.data;

public class TrainingJavaModel {
    public String getName() {
        return name;
    }

    public TrainingJavaModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getPlace() {
        return place;
    }

    public TrainingJavaModel setPlace(String place) {
        this.place = place;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public TrainingJavaModel setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getTrainer() {
        return trainer;
    }

    public TrainingJavaModel setTrainer(String trainer) {
        this.trainer = trainer;
        return this;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public TrainingJavaModel setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
        return this;
    }

    private String name;
    private String place;
    private int price;
    private String trainer;
    private int maxParticipants;
}
