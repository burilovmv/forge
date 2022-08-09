package forge.server;

import com.google.common.base.Function;
import forge.ImageKeys;
import forge.gamemodes.match.HostedMatch;
import forge.gui.FThreads;
import forge.gui.download.GuiDownloadService;
import forge.gui.interfaces.IGuiBase;
import forge.gui.interfaces.IGuiGame;
import forge.item.PaperCard;
import forge.localinstance.skin.FSkinProp;
import forge.localinstance.skin.ISkinImage;
import forge.sound.IAudioClip;
import forge.sound.IAudioMusic;
import forge.util.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

public class GuiServer implements IGuiBase {
    //private ImageFetcher imageFetcher = new LibGDXImageFetcher(); //todo make sure if we need it

    @Override
    public boolean isRunningOnDesktop() {
        return false;
    }

    @Override
    public boolean isLibgdxPort() {
        return false;
    }

    @Override
    public String getCurrentVersion() {
        return BuildInfo.getVersionString();
    }

    @Override
    public String getAssetsDir() {
        return StringUtils.containsIgnoreCase(BuildInfo.getVersionString(), "git") ?
                // FIXME: replace this hardcoded value!!
                "../forge-gui/" : "";
    }

    @Override
    public ImageFetcher getImageFetcher() {
        return null;
    }

    @Override
    public void invokeInEdtNow(final Runnable proc) {
        proc.run();
    }

    @Override
    public void invokeInEdtLater(final Runnable proc) {
        new WaitRunnable() {
            @Override
            public void run() {
                proc.run();
            }
        }.run();
    }

    @Override
    public void invokeInEdtAndWait(final Runnable proc) {
        proc.run();
    }

    @Override
    public boolean isGuiThread() {
        return true;
    }

    @Override
    public ISkinImage getSkinIcon(final FSkinProp skinProp) {
        return null;
    }

    @Override
    public ISkinImage getUnskinnedIcon(final String path) {
        return null;
    }

    @Override
    public ISkinImage getCardArt(final PaperCard card) {
        return null; //TODO
    }

    @Override
    public ISkinImage getCardArt(final PaperCard card, final boolean backFace) {
        return null; //TODO
    }

    @Override
    public ISkinImage createLayeredImage(final FSkinProp background, final String overlayFilename, final float opacity) {
        return null;
    }

    @Override
    public void showImageDialog(final ISkinImage image, final String message, final String title) {
    }

    @Override
    public int showOptionDialog(final String message, final String title, final FSkinProp icon, final java.util.List<String> options, final int defaultOption) {
        return defaultOption;
    }

    @Override
    public String showInputDialog(final String message, final String title, final FSkinProp icon, final String initialInput, final java.util.List<String> inputOptions) {
        return null;
    }

    @Override
    public <T> java.util.List<T> getChoices(final String message, final int min, final int max, final Collection<T> choices, final T selected, final Function<T, String> display) {
        return null;
    }

    @Override
    public <T> java.util.List<T> order(final String title, final String top, final int remainingObjectsMin, final int remainingObjectsMax,
                                       final java.util.List<T> sourceChoices, final java.util.List<T> destChoices) {
        return null;
    }

    @Override
    public void showCardList(final String title, final String message, final java.util.List<PaperCard> list) {
    }

    @Override
    public boolean showBoxedProduct(final String title, final String message, final java.util.List<PaperCard> list) {
        return false;
    }

    @Override
    public PaperCard chooseCard(String title, String message, List<PaperCard> list) {
        return null;
    }

    @Override
    public int getAvatarCount() {
        return 0;
    }

    @Override
    public int getSleevesCount() {
        return 0;
    }

    @Override
    public String showFileDialog(final String title, final String defaultDir) {
        return null;
    }

    @Override
    public void showBugReportDialog(final String title, final String text, final boolean showExitAppBtn) {
    }

    @Override
    public File getSaveFile(final File defaultFile) {
        return null;
    }

    @Override
    public void download(final GuiDownloadService service, final Callback<Boolean> callback) {
        //todo not sure if we need it
    }

    @Override
    public void refreshSkin() {
        //todo refresh skin selector
    }

    @Override
    public void copyToClipboard(final String text) {
    }

    @Override
    public void browseToUrl(final String url) throws IOException, URISyntaxException {
    }

    @Override
    public IAudioClip createAudioClip(final String filename) {
        return null;
    }

    @Override
    public IAudioMusic createAudioMusic(final String filename) {
        return null;
    }

    @Override
    public void startAltSoundSystem(final String filename, final boolean isSynchronized) {
    }

    @Override
    public void clearImageCache() {
        ImageKeys.clearMissingCards();
    }

    @Override
    public void showSpellShop() {
    }

    @Override
    public void showBazaar() {
    }

    @Override
    public IGuiGame getNewGuiGame() {
        return null;
    }

    @Override
    public HostedMatch hostMatch() {
        final HostedMatch match = new HostedMatch();
        //Singletons.getControl().addMatch(match);
        return match;
    }

    @Override
    public void runBackgroundTask(String message, Runnable task) {
        //TODO: Show loading overlay
        FThreads.invokeInBackgroundThread(task);
    }

    @Override
    public String encodeSymbols(String str, boolean formatReminderText) {
        return str; //TODO: what is this?
    }

    @Override
    public void preventSystemSleep(boolean preventSleep) {
        OperatingSystem.preventSystemSleep(preventSleep);
    }

    @Override
    public float getScreenScale() {
        return 1.0f;
    }
}
