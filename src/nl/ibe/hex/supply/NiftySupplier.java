/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.supply;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.radiobutton.builder.RadioButtonBuilder;
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
        final String radioGroup = "TeamRadio";
        
        //What panel should be used?
        //If there are less than 5 teams, use the center
        
        int count = TeamManager.getVisibleCount();
        
        if(count <= 5) {
            
            Element panel = nifty.getCurrentScreen().findElementById("radioCenter");
            
            //Build!
            Team[] allTeams = TeamManager.getVisibleTeams().toArray(new Team[count]);
            
            
            
            for (final Team team : allTeams) {
                
                final String name = team.getName();
                final String id = team.getNumericalID() + "";
                
                PanelBuilder panelBuild = new PanelBuilder("radio" + id + "Panel") {{
                    
                    width("100%");
                    height((int)(100/count) + "%");
                    childLayoutHorizontal();
                    backgroundColor("#000a");
                    alignCenter();
                    
                    
                    
                    control(new LabelBuilder("radio" + id + "Label", name) {{
                        
                        valignCenter();
                        width("60%");
                        
                    }});
                    
                    control(new RadioButtonBuilder("radio" + id) {{
                        
                        valignCenter();
                        width("40%");
                        group(radioGroup);
                        
                    }});
                }};
                
                panelBuild.build(nifty, nifty.getCurrentScreen(), panel);
                
//                LabelBuilder lb = new LabelBuilder("label" +i , "label");
//                lb.build(nifty, nifty.getCurrentScreen(), panel);
                
                //i++;
            }
            
            nifty.getCurrentScreen().layoutLayers();
        }
        
    }
    
}
