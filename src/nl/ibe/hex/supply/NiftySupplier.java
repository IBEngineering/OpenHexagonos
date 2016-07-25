/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.supply;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.dynamic.PanelCreator;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.elements.Element;
import nl.ibe.hex.game.player.Team;
import nl.ibe.hex.game.player.TeamManager;

/**
 *
 * @author MisterCavespider
 */
public class NiftySupplier {
    
    public static void addTeams(Nifty nifty, String elementId) {
        
        //There should be a radio button group called:
        String radioGroup = "teamRadio";
        
        //What panel should be used?
        //If there are less than 5 teams, use the center
        
        int count = TeamManager.getVisibleCount();
        
        if(count <= 5) {
            
            Element panel = nifty.getCurrentScreen().findElementById("radioCenter");
            
            //Build!
            Team[] allTeams = TeamManager.getVisibleTeams().toArray(new Team[count]);
            
            for (Team team : allTeams) {
                
//                PanelBuilder panelBuild = new PanelBuilder("radio" + team.getID() + "Panel") {{
//                    
//                    width("100%");
//                    height((int)(100/count) + "%");
//                    childLayoutHorizontal();
//                    backgroundColor("#000a");
//                    
//                    
//                    
//                    control(new LabelBuilder("radio" + team.getID() + "Label", team.getName()) {{
//                        
//                        valignCenter();
//                        width("60%");
//                        
//                    }});
//                    
//                    control(new LabelBuilder("radio" + team.getID(), team.getName()) {{
//                        
//                        valignCenter();
//                        width("40%");
//                        
//                    }});
//                }};
                
                (new LabelBuilder("id", "text")).build(nifty, nifty.getCurrentScreen(), nifty.getCurrentScreen().findElementById("radioCenter"));
                
            }
            
            nifty.getCurrentScreen().layoutLayers();
        }
        
    }
    
}
