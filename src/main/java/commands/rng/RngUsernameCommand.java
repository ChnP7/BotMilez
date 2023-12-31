package commands.rng;

import commands.IBotCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import util.IO;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * =============== RNG Username ===============
 * Slash Command that generates a random username by
 * creating random combinations of words and numbers
 * in certain orders, and randomly deciding if the name
 * should follow a specific style such as all caps.
 */
public class RngUsernameCommand implements IBotCommand {

    private String[] adjectives;
    private String[] nounsPeople;
    private String[] nounsThings;
    private String[] titles;

    private String[] determiners;
    private final String PATH_DIR = "resources/sentencegenerators/words/";

    public RngUsernameCommand() {
        adjectives = IO.readAllFileLinesIntoArray(PATH_DIR + "adjectives.txt");
        nounsPeople = IO.readAllFileLinesIntoArray(PATH_DIR + "nounPerson.txt");
        nounsThings = IO.readAllFileLinesIntoArray(PATH_DIR + "nounThing.txt");
        titles = IO.readAllFileLinesIntoArray(PATH_DIR + "titles.txt");
        determiners = IO.readAllFileLinesIntoArray(PATH_DIR + "det.txt");
    }

    @Override
    public String getName() {
        return "rng-username";
    }

    @Override
    public String getDesc() {
        return "Sends a randomly-generated username";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void doAction(SlashCommandInteractionEvent event) {
        StringBuilder name = new StringBuilder();
        Random rand = new Random();

        boolean doAdjective = rand.nextInt(2) == 1; /* 50% chance */
        boolean doNounThing = rand.nextInt(4) != 3; /* 75% chance */
        boolean doNounPerson = rand.nextInt(2) == 1; /* 25% chance */
        boolean doTitle = rand.nextInt(4) == 1; /* 25% chance */
        boolean doNumber = rand.nextInt(3) == 1; /* 33% chance */
        boolean doUnderscores = rand.nextInt(5) == 1; /* 20% chance */
        boolean doDeterminer = rand.nextInt(10) == 1; /* 10% chance */
        boolean doAllCaps = rand.nextInt(5) == 1; /* 20% chance */
        boolean doDoubleX = rand.nextInt(10) == 1; /* 10% chance */

        /* Avoid awkward situation with an empty username, or only numbers */
        if (!doAdjective && !doNounThing && !doNounPerson && !doTitle) {
            doNounThing = true;
        }

        /*
         * Potential orders depending on what was chosen:
         *
         * (det)(adj)(title)(nounThing)(nounPerson)(number)
         */

        if (doDeterminer) {
            name.append(determiners[rand.nextInt(determiners.length)]);
            if (doUnderscores)
                name.append("_");
        }

        if (doAdjective) {
            name.append(adjectives[rand.nextInt(adjectives.length)]);
            if (doUnderscores)
                name.append("_");
        }

        if (doTitle) {
            name.append(titles[rand.nextInt(titles.length)]);
            if (doUnderscores)
                name.append("_");
        }

        if (doNounThing) {
            name.append(nounsThings[rand.nextInt(nounsThings.length)]);
            if (doUnderscores)
                name.append("_");
        }

        if (doNounPerson) {
            name.append(nounsPeople[rand.nextInt(nounsPeople.length)]);
            if (doUnderscores)
                name.append("_");
        }

        if (doNumber) {
            name.append(Integer.toString(rand.nextInt(100)));
        }

        if (doDoubleX) {
            name.append("Xx");
            name.insert(0,"xX");
        }

        String result = name.toString();

        if (doAllCaps)
            result = result.toUpperCase();


        event.reply(result).queue();

    }

    @Override
    public void getHelp(StringSelectInteractionEvent event) {
        String overview = "This command generates a random username when used. " +
                "Many potential components of a username such as an adjective, a name, " +
                "a number, underscores, etc. are put together in a random fashion, creating the username." +
                " Most components of a username only have a chance of appearing with each use of the command. " +
                "Underscores, for example, have a 20% chance of appearing.";

        EmbedBuilder emBuilder = new EmbedBuilder();
        emBuilder.setTitle("/" + getName());
        emBuilder.setDescription(getDesc());
        emBuilder.setColor(Color.BLUE);
        emBuilder.setFooter("\"Username is taken\" aw great, just great.");
        emBuilder.addField(new MessageEmbed.Field(
                "Command Info",
                overview,
                false
        ));

        event.editMessageEmbeds(emBuilder.build()).setComponents().queue();
    }
}
