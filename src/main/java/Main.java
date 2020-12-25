import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main extends ListenerAdapter {
    private Game_Info game=new Game_Info();
    public static void main(String[] args) throws LoginException {
        File tokenFile=new File("C:\\Users\\shrey\\IdeaProjects\\present-bot-improved\\data\\token.txt");
        String token="";

        try {
            Scanner reader = new Scanner(tokenFile);
            token = reader.nextLine();
            reader.close();
        }
        catch (FileNotFoundException fnfe){
            System.out.println("File not found");
            fnfe.printStackTrace();
        }

        //Activity custom= Activity.of(Activity.ActivityType., "Open Me! (*open)");
        //new 4.2.0 version way
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.setActivity(Activity.watching(" snow fall outside. Open Me! (*open)"));
        builder.addEventListeners(new Main());
        builder.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        //no infinite message loop
        if(event.getAuthor().isBot()){
            return;
        }

        game.eventHandling(event);
    }
}

