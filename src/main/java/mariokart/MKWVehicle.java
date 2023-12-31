package mariokart;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * A class that represents a Mario Kart Wii vehicle & Its stats.
 * All stats of a vehicle are final, so there are no setter methods. Values
 * of fields are set once in the constructor when the object is created.
 */
public final class MKWVehicle implements Comparable<MKWVehicle>{
    private final long id; /* vehicle's internal id */
    private final String name; /* name of vehicle */
    private final String alias; /* Alternative name, given in EU version */
    private final String codename; /* internal name string */
    private final String type; /* Bike or Kart */
    private final String weightclass; /* Light, Medium, Heavy */
    private final String drifttype; /* Inward or Outward */


    /* The higher the integer, the higher the performance of below stats */
    private final long speed;
    private final long weight;
    private final long acceleration;
    private final long handling;
    private final long drift;
    private final long offroad;
    private final long miniturbo;
    private final long total;
    private final String imgURL;
    private final String imgcredit;


    public MKWVehicle(long id, String name, String alias, String codename,
                      String type, String weightclass, String driftype,
                      long speed, long weight, long acceleration, long handling,
                      long drift, long offroad, long miniturbo, long total,
                      String imgURL, String imgcredit) {

        this.id = id;
        this.name = name;
        this.alias = alias;
        this.codename = codename;
        this.type = type;
        this.weightclass = weightclass;
        this.drifttype = driftype;
        this.speed = speed;
        this.weight = weight;
        this.acceleration = acceleration;
        this.handling = handling;
        this.drift = drift;
        this.offroad = offroad;
        this.miniturbo = miniturbo;
        this.total = total;
        this.imgURL = imgURL;
        this.imgcredit = imgcredit;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getCodename() {
        return codename;
    }

    public String getType() {
        return type;
    }

    public String getWeightclass() {
        return weightclass;
    }

    public String getDrifttype() {
        return drifttype;
    }

    public long getSpeed() {
        return speed;
    }

    public long getAcceleration() {
        return acceleration;
    }

    public long getWeight() {
        return weight;
    }

    public long getHandling() {
        return handling;
    }

    public long getDrift() {
        return drift;
    }

    public long getOffroad() {
        return offroad;
    }

    public long getMiniturbo() {
        return miniturbo;
    }

    public long getTotal() {
        return total;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getImgcredit() {
        return imgcredit;
    }

    public MessageEmbed.Field getAsField() {
        String desc =
                "*ID*:　　　　　　　" + id + "\n" +
                        "*Alias*:　　　　　　" + alias + "\n" +
                        "*Internal name*:　　" + codename + "\n" +
                        "*Type*:　　　　　　" + type + "\n" +
                        "*Weight Class*:　　　" + weightclass + "\n" +
                        "*Drift Type*:　　　　" + drifttype + "\n" +
                        "*Speed*:　　　　　" + speed + "\n" +
                        "*Acceleration*:　　　" + acceleration + "\n" +
                        "*Weight*:　　　　　" + weight + "\n" +
                        "*Handling*:　　　　" + handling + "\n" +
                        "*Drift*:　　　　　　" + drift + "\n" +
                        "*Offroad*:　　　　　" + offroad + "\n" +
                        "*Miniturbo*:　　　　" + miniturbo + "\n" +
                        "*Total*:　　　　　　" + total;

        MessageEmbed.Field field = new MessageEmbed.Field(
                name,
                desc,
                false
        );
        return field;
    }

    @Override
    public int compareTo(MKWVehicle other) {
        /* Ordering done by name instead of id */
        return name.compareTo(other.getName());
    }
}