import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;

public class Game_Info {
    int NUM_INGREDIENTS;
    int level, ingredient;
    boolean ingredient_description;
    EmbedBuilder embed;
    EmbedBuilder ending;
    String EPY_ID, OWNER_ID, VALID_ID;
    String phrases[];
    String answers[];
    File images[];
    File honeycake;

    Game_Info(){
        NUM_INGREDIENTS=7;
        level=0;
        ingredient=0;
        ingredient_description=true;

        embed=new EmbedBuilder();
        embed.setTitle(":mrs_claus_tone3: | Ms.Kringle");
        embed.setColor(0x49d1a4);

        ending=new EmbedBuilder();
        ending.setTitle(":e_mail: | Message from KR");
        ending.setColor(0xb82441);
        ending.setDescription("Thank you for being such an awesome friend! You're always considerate of how I am feeling and take interest in the things I like. I really enjoy spending time with you listening to Kpop and playing Santorini. I also like that you belly laugh at most of my jokes (even when they aren't that funny). I hope you know how important you are to the entire world and to me! Also I own you. K bye.");

        EPY_ID="541938416577806347";
        OWNER_ID="412111697013243917";
        VALID_ID="";

        phrases=new String[NUM_INGREDIENTS-1];
        phrases[0]="Flour or Cement?";
        phrases[1]="Sugar, Salt, Hot Sauce, or Cupcake?";
        phrases[2]="Butter, Mayonnaise, or Popcorn?";
        phrases[3]="Yogurt, Egg, or Broccoli?";
        phrases[4]="Baking Powder or Baking Soda?";
        phrases[5]="Cheese, Cream, Strawberries, or Chocolate?";

        answers=new String[]{"flour", "sugar", "butter", "egg", "baking soda", "cream", "honey"};

        images=new File[NUM_INGREDIENTS];
        images[0]=new File("C:\\Users\\shrey\\IdeaProjects\\present-bot-improved\\images\\Ingredients\\1.png");
        images[1]=new File("C:\\Users\\shrey\\IdeaProjects\\present-bot-improved\\images\\Ingredients\\2.png");
        images[2]=new File("C:\\Users\\shrey\\IdeaProjects\\present-bot-improved\\images\\Ingredients\\3.png");
        images[3]=new File("C:\\Users\\shrey\\IdeaProjects\\present-bot-improved\\images\\Ingredients\\4.png");
        images[4]=new File("C:\\Users\\shrey\\IdeaProjects\\present-bot-improved\\images\\Ingredients\\5.png");
        images[5]=new File("C:\\Users\\shrey\\IdeaProjects\\present-bot-improved\\images\\Ingredients\\6.png");
        images[6]=new File("C:\\Users\\shrey\\IdeaProjects\\present-bot-improved\\images\\Ingredients\\honey.gif");
        honeycake=new File("C:\\Users\\shrey\\IdeaProjects\\present-bot-improved\\images\\honeycake.png");
    }

    void eventHandling(MessageReceivedEvent event){
        String message=event.getMessage().getContentRaw();
        String authorID=event.getAuthor().getId();
        MessageChannel channel=event.getChannel();
        String correct="put ";

        if(message.equalsIgnoreCase("*open")&&!(authorID.equals(EPY_ID)|| authorID.equals(OWNER_ID))){
            embed.clearFields();
            embed.addField("This is not your present!","",false);
            channel.sendMessage(embed.build()).queue();
            return;
        }

        if(message.equalsIgnoreCase("*open")){
            clear();
            VALID_ID=authorID;
            level++;
            embed.addField("Hello there my precious elf! I'm Ms.Kringle.",
                    "Will you help me create a special recipe for christmas (Y/N)?",
                    false);
            channel.sendMessage(embed.build()).queue();
            return;
        }

        if(level==1&&authorID.equals(VALID_ID)){
            embed.clearFields();
            if (message.equalsIgnoreCase("yes") || message.equalsIgnoreCase("y")) {
                embed.addField("Thank you so much for your help! Let's get cookin'.",
                        "",
                        false);
                embed.addField("We are making a Christmas cake. Put the correct ingredients in the bowl by typing 'put [ingredient name]'",
                        " <Press any key to continue> ",
                        false);
                level++;
            }
            else if(message.equalsIgnoreCase("no") || message.equalsIgnoreCase("n")){
                embed.addField("Well that is unfortunate because you are the only one that can help me. So suck it up and let's get cookin'!",
                        "",
                        false);
                embed.addField("We are making a Christmas cake. Put the correct ingredients in the bowl by typing 'put [ingredient name]'",
                        " <Press any key to continue> ",
                        false);
                level++;
            }
            else {
                embed.addField("That is not a valid response! Please say yes or no.",
                        "valid response types: yes, no, y, n ",
                        false);
            }
            channel.sendMessage(embed.build()).queue();
            embed.clearFields();
            return;
        }

        if(level==2&&authorID.equals(VALID_ID)&&!ingredient_description) {

            correct += answers[ingredient];
            if (message.equalsIgnoreCase(correct)) {
                ingredient++;
                if (answers[ingredient].equals("honey")) {
                    level++;
                }
                embed.addField("That is correct!", "", false);
                ingredient_description = true;
            } else {
                embed.addField("That is not correct, but you can try again.", "", false);
                channel.sendMessage(embed.build()).queue();
                embed.clearFields();
                return;
            }
        }

        if(level==2&&authorID.equals(VALID_ID)&&ingredient_description){
                ingredient_description=false;
                embed.addField(phrases[ingredient], "", false);
                embed.setImage("attachment://ingredient.png");
                channel.sendMessage(embed.build()).addFile(images[ingredient], "ingredient.png").queue();

                embed.clearFields();
                return;
        }

        if(level==3&&authorID.equals(VALID_ID)) {
            embed.clearFields();
            if (ingredient_description) {
                ingredient_description = false;

                embed.setImage("attachment://honey.gif");
                embed.setTitle(":rosette: The Final Magical Ingredient :rosette:");
                embed.setDescription("**Epraksian Golden Honey**");
                embed.addField(" >> The Epraksia family of plants are known for their for joyful glow which affect the heart. When added to a dish, they have the effect of bringing warmth and mirth to those that consume it.",
                        "Put 'honey' as the last ingredient",
                        false);
                channel.sendMessage(embed.build()).addFile(images[ingredient], "honey.gif").queue();

                return;
            } else {
                correct += answers[ingredient];
                if (message.equalsIgnoreCase(correct)) {
                    ingredient = 0;
                    level++;
                    ingredient_description = true;

                    embed.setImage("attachment://honeycake.png");
                    embed.setTitle(":mrs_claus_tone3: | Ms.Kringle");
                    embed.addField("What a beautiful Christmas honey cake you've created. For your incredible work, I shall honor you with the title of 'Epy the Helpy Elf'.",
                            "<Press any key to continue>", false);
                    channel.sendMessage(embed.build()).addFile(honeycake, "honeycake.png").queue();

                    return;
                } else {
                    embed.addField("That is not correct, but you can try again!", "", false);
                    channel.sendMessage(embed.build()).queue();
                    embed.clearFields();
                    return;
                }
            }
        }

        if(level==4&&authorID.equals(VALID_ID)){
            clear();
            channel.sendMessage(ending.build()).queue();
            return;
        }

    }

    void clear(){
        level=0;
        ingredient=0;
        ingredient_description=true;
        VALID_ID="";

        embed.clear();
        embed.setTitle(":mrs_claus_tone3: | Ms.Kringle");
        embed.setColor(0xb82441);
    }


}
