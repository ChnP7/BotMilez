package commands.rng;

import commands.IBotCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * =============== RNG ===============
 * Represents a Discord Slash Command that allows the user to
 * generate a random number. A lower bound, upper bound, or a
 * specific range of the random number to generate can be specified
 * by the user also. The lowest and highest number that is allowed
 * to be generated is the same as the limits that an integer can
 * represent.
 * The command also allows a user to input a list of options and the
 * bot will randomly select one option from the list.
 *
 */
public class RngCommand implements IBotCommand {

    private List<OptionData> options;
    private final String MAX_OPTION = "upper_bound";
    private final String MIN_OPTION = "lower_bound";
    private final String STRINGS_OPTION = "list_of_options";

    public RngCommand() {
        options = new ArrayList<>();

        options.add(
                new OptionData(OptionType.INTEGER, MAX_OPTION,
                        "Highest possible number to generate",
                        false)
                        .setMaxValue(Integer.MAX_VALUE - 1));

        options.add(
                new OptionData(OptionType.INTEGER, MIN_OPTION,
                        "Lowest possible number to generate",
                        false)
                        .setMinValue(1));

        options.add(
                new OptionData(OptionType.STRING, STRINGS_OPTION,
                        "list of options to choose from",
                        false)
        );

    }

    @Override
    public String getName() {
        return "rng";
    }

    @Override
    public String getDesc() {
        return "Generate a random number within range specified by options (inclusive) or" +
                " between 0 and 2147483647";
    }

    @Override
    public List<OptionData> getOptions() {
        return options;
    }

    @Override
    public void doAction(SlashCommandInteractionEvent event) {

        int max = Integer.MAX_VALUE;
        int min = 0;

        boolean maxSpecified = event.getOption(MAX_OPTION) != null;
        boolean minSpecified = event.getOption(MIN_OPTION) != null;

        Random rand = new Random();


        if (!maxSpecified && !minSpecified) {
            if (event.getOption(STRINGS_OPTION) != null) {
                String[] options = event.getOption(STRINGS_OPTION).getAsString().split("\\s+");
                event.reply(options[rand.nextInt(options.length)]).queue();
            }
            else {
                event.reply(Integer.toString(rand.nextInt(max))).queue();
            }
        }

        else if (!minSpecified) {
            max = event.getOption(MAX_OPTION).getAsInt() + 1; /* To make upper bound inclusive */
            event.reply(Integer.toString(rand.nextInt(max))).queue();
        }

        else if (!maxSpecified) {
            min = event.getOption(MIN_OPTION).getAsInt();
            event.reply(Integer.toString(rand.nextInt(max - min) + min)).queue();
        }

        /* Meaning both max and min were specified i.e. an explicit range */
        else {
            max = event.getOption(MAX_OPTION).getAsInt() + 1;
            min = event.getOption(MIN_OPTION).getAsInt();

            if (min > max) {
                event.reply("Error: Lower bound cannot be more than upper bound").queue();
                return;
            }

            event.reply(Integer.toString(rand.nextInt(max - min) + min)).queue();
        }

    }

    @Override
    public void getHelp(StringSelectInteractionEvent event) {

        String info = "This command generates a random number. A user can specify " +
                "a range of possible numbers that can be generated For example, get a random" +
                " number up to 10, or get a random number between 24-31. If a specific range " +
                "or bound is not specified, then this command generates a random number from " +
                "the range 0 to 2147483647. \n" +
                "This command can also randomly selected an object from a list of things. Just " +
                "type the list of objects after the slash command, each one separated by a space.";

        String options = "Options that a user can specify after the slash command:\n" +
                "**lower_bound**: minimum possible random number that can be generated (inclusive)\n" +
                "**upper_bound**: maximum possible random number that can be generated (inclusive)\n" +
                "**list_of_options**: List of objects to pick from, each separated by a space.";

        String examples = "Example usages:\n\n" +
                "Get a number between 0 to 2147483647 (default)\n" +
                "```/" + getName() + "```\n" +
                "Get a number from 0 to 20:\n" +
                "```/" + getName() + " 20```\n" +
                "Get a number between 46 and 91:\n" +
                "```/" + getName() + " 46 91```\n" +
                "Choose one from the list: a, b, c, d:\n" +
                "```/" + getName() + " a b c d```" +
                "Remember to use the correct options (e.g. upper_bound for a maximum limit) when typing" +
                "the command for your purposes.";

        EmbedBuilder emBuilder = new EmbedBuilder();
        emBuilder.setTitle("/" + getName());
        emBuilder.setDescription(getDesc());
        emBuilder.setColor(Color.BLUE);
        emBuilder.setFooter("RNG is always against me!");
        emBuilder.addField(new MessageEmbed.Field(
                "Command Info",
                info,
                false
        ));
        emBuilder.addField(new MessageEmbed.Field(
                "Command options",
                options,
                false
        ));
        emBuilder.addField(new MessageEmbed.Field(
                "Example Usages",
                examples,
                false
        ));

        event.editMessageEmbeds(emBuilder.build()).setComponents().queue();
    }
}
