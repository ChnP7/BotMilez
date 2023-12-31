package botmilez;

import commands.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

import java.util.List;
import java.util.Map;

import static botmilez.config.BOTMILEZ_TOKEN;

//TODO: rng-sentence command including Markov & Basic generator
//TODO: Help command describing each command with examples
//TODO: Music command
//TODO: Random image command by scraping the web similar to old university project
//TODO: ImposterBot idea
public class BotMain {
    public static void main(String[] args) throws LoginException {

        JDABuilder jdaBuilder = JDABuilder.createDefault(BOTMILEZ_TOKEN);

        CommandManager cmdManager = new CommandManager();
        List<IBotCommand> commands = cmdManager.getCommands();

        /*
         * Add event listeners, including the main command manager
         * And commands that have their own listeners.
         */
        jdaBuilder = jdaBuilder.addEventListeners(cmdManager);
        for (IBotCommand command : commands) {
            if (command instanceof ListenerAdapter) {
                jdaBuilder = jdaBuilder.addEventListeners(command);
            }
        }

        /* Build the JDA */
        JDA jda = jdaBuilder.setActivity(Activity.watching("Fresh Pillow"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                .build();


    }


}
