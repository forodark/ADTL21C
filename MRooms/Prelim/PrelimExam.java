import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class PrelimExam extends Applet {
    private String title = "Wake Me Up When September Ends";
    private String[] songStanzas = {
        "Summer has come and passed",
        "The innocent can never last",
        "Wake me up when September ends",
        "",
        "Like my father's come to pass",
        "Seven years have gone so fast",
        "Wake me up when September ends"
    };

    Font stanzaFont = new Font("Arial", Font.BOLD, 16);
    Font lineFont = new Font("Times New Roman", Font.ITALIC, 18);
    
    public void init() {
        // Set dark gray background color in the init method
        setBackground(new Color(30, 30, 30));
    }

    public void paint(Graphics g) {
        g.setFont(stanzaFont);
        g.setColor(Color.white);

        int lineHeight = 25;
        int y = 50;

        g.drawString(title, 20, y);
        y += lineHeight;

        y = drawLine(g, y, lineHeight);

        for (String stanza : songStanzas) {
            if (stanza.isEmpty()) {
                y += lineHeight;
            } else {
                g.drawString(stanza, 20, y);
                y += lineHeight;
            }
        }
        
        y = drawLine(g, y, lineHeight);
    }

    public int drawLine(Graphics g, int y, int lineHeight) {
        g.setFont(lineFont);
        g.setColor(Color.yellow);

        g.drawLine(20, y + 10, getWidth() - 20, y + 10);

        g.setFont(stanzaFont);
        g.setColor(Color.white);

        return y + lineHeight + 25;
    }
}
