package me.hugmanrique.pokedata.utils;

/**
 * @author Hugmanrique
 * @since 28/05/2017
 */
public class ReadableROM implements ROM {
    private byte[] bytes;
    private int internalOffset;

    private byte[] romHeader;
    private byte freeSpaceByte = (byte) 0xFF;

    private String headerCode;
    private String headerName;
    private String headerMaker;

    private boolean rtcAdded = false;
    private boolean dnPokePatchAdded = false;

    @Override
    public byte[] getData() {
        return bytes;
    }

    @Override
    public void setData(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public int getInternalOffset() {
        return internalOffset;
    }

    @Override
    public void setInternalOffset(int offset) {
        this.internalOffset = offset;
    }

    @Override
    public byte getFreeSpaceByte() {
        return freeSpaceByte;
    }

    @Override
    public void writeByte(byte value, int offset) {
        // Read only ROM
    }

    @Override
    public void setCurrentHeader(byte[] romHeader) {
        this.romHeader = romHeader;
    }

    @Override
    public byte[] getCurrentHeader() {
        return romHeader;
    }

    @Override
    public String getGameCode() {
        return headerCode;
    }

    @Override
    public String getGameText() {
        return headerName;
    }

    @Override
    public String getGameCreatorId() {
        return headerMaker;
    }

    @Override
    public boolean isDNPokePatchAdded() {
        return dnPokePatchAdded;
    }

    @Override
    public boolean isRTCAdded() {
        return rtcAdded;
    }

    @Override
    public void setDNPokePatchAdded(boolean added) {
        this.dnPokePatchAdded = added;
    }

    @Override
    public void setRTCAdded(boolean added) {
        this.rtcAdded = added;
    }

    @Override
    public void setGameVars(String code, String name, String maker) {
        this.headerCode = code;
        this.headerName = name;
        this.headerMaker = maker;
    }
}
