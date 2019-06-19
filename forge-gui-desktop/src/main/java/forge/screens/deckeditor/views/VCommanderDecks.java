package forge.screens.deckeditor.views;

import forge.deck.io.DeckPreferences;
import forge.game.GameType;
import forge.gui.framework.DragCell;
import forge.gui.framework.DragTab;
import forge.gui.framework.EDocID;
import forge.gui.framework.IVDoc;
import forge.itemmanager.DeckManager;
import forge.itemmanager.ItemManagerContainer;
import forge.screens.deckeditor.controllers.CCommanderDecks;
import forge.screens.match.controllers.CDetailPicture;
import forge.util.Localizer;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/** 
 * Assembles Swing components of all deck viewer in deck editor.
 *
 * <br><br><i>(V at beginning of class name denotes a view class.)</i>
 */
public enum VCommanderDecks implements IVDoc<CCommanderDecks> {
    /** */
    SINGLETON_INSTANCE;

    // Fields used with interface IVDoc
    private DragCell parentCell;
    final Localizer localizer = Localizer.getInstance();
    private final DragTab tab = new DragTab(localizer.getMessage("lblCommander"));

    private DeckManager lstDecks;

    //========== Overridden methods

    /* (non-Javadoc)
     * @see forge.gui.framework.IVDoc#getDocumentID()
     */
    @Override
    public EDocID getDocumentID() {
        return EDocID.EDITOR_COMMANDER;
    }

    /* (non-Javadoc)
     * @see forge.gui.framework.IVDoc#getTabLabel()
     */
    @Override
    public DragTab getTabLabel() {
        return tab;
    }

    /* (non-Javadoc)
     * @see forge.gui.framework.IVDoc#getLayoutControl()
     */
    @Override
    public CCommanderDecks getLayoutControl() {
        return CCommanderDecks.SINGLETON_INSTANCE;
    }

    /* (non-Javadoc)
     * @see forge.gui.framework.IVDoc#setParentCell(forge.gui.framework.DragCell)
     */
    @Override
    public void setParentCell(DragCell cell0) {
        this.parentCell = cell0;
    }

    /* (non-Javadoc)
     * @see forge.gui.framework.IVDoc#getParentCell()
     */
    @Override
    public DragCell getParentCell() {
        return this.parentCell;
    }

    /* (non-Javadoc)
     * @see forge.gui.framework.IVDoc#populate()
     */
    @Override
    public void populate() {
        CCommanderDecks.SINGLETON_INSTANCE.refresh(); //ensure decks refreshed in case any deleted or added since last loaded

        JPanel parentBody = parentCell.getBody();
        parentBody.setLayout(new MigLayout("insets 5, gap 0, wrap, hidemode 3"));
        parentBody.add(new ItemManagerContainer(lstDecks), "push, grow");
        String preferredDeck = DeckPreferences.getCommanderDeck();
        lstDecks.editDeck(lstDecks.stringToItem(preferredDeck));
    }

    //========== Retrieval methods
    /** @return {@link JPanel} */
    public DeckManager getLstDecks() {
        return lstDecks;
    }

    public void setCDetailPicture(final CDetailPicture cDetailPicture) {
        this.lstDecks = new DeckManager(GameType.Commander, cDetailPicture);
        this.lstDecks.setCaption(localizer.getMessage("lblCommanderDecks"));
    }
}
