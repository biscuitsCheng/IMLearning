package cn.corey.demo.day2;


import java.nio.charset.StandardCharsets;

public class Codec {
    private final byte[] codec;
    private static final byte[] prefix = "COREY".getBytes(StandardCharsets.UTF_8);
    private static final int MAGIC_NUM_LENGTH = prefix.length;

    public byte[] getCodec() {
        return codec;
    }

    private Codec(byte value, byte[] text) {

        int length = text.length;
        codec = new byte[MAGIC_NUM_LENGTH + 1 + 4 + length];
        initCode(codec);
        codec[MAGIC_NUM_LENGTH] = value;
        codec[MAGIC_NUM_LENGTH + 1] = (byte) ((length >> 24) & 0xFF);
        codec[MAGIC_NUM_LENGTH + 2] = (byte) ((length >> 16) & 0xFF);
        codec[MAGIC_NUM_LENGTH + 3] = (byte) ((length >> 8) & 0xFF);
        codec[MAGIC_NUM_LENGTH + 4] = (byte) ((length) & 0xFF);
        System.arraycopy(text, 0, codec, MAGIC_NUM_LENGTH + 5, length);
    }


    private Codec(byte value, byte text) {
        codec = new byte[MAGIC_NUM_LENGTH + 2];
        initCode(codec);
        codec[MAGIC_NUM_LENGTH] = value;
        codec[MAGIC_NUM_LENGTH + 1] = text;
    }

    public static Codec text(byte[] code) {
        return new Codec(CodeType.TEXT.value, code);
    }

    public static Codec error(byte[] code) {
        return new Codec(CodeType.ERROR.value, code);
    }

    public static Codec code(byte code) {
        return new Codec(CodeType.CODE.value, code);
    }

    public byte[] getText() {
        if (codec[MAGIC_NUM_LENGTH] != CodeType.TEXT.value) {
            throw new RuntimeException("Not a text");
        }
        return getResult(codec);
    }

    public byte[] getError() {
        if (codec[MAGIC_NUM_LENGTH] != CodeType.ERROR.value) {
            throw new RuntimeException("Not a error");
        }
        return getResult(codec);
    }

    public byte getCode() {
        if (codec[MAGIC_NUM_LENGTH] != CodeType.CODE.value) {
            throw new RuntimeException("Not a code");
        }
        checkMagic(codec);
        return codec[MAGIC_NUM_LENGTH + 1];
    }

    private byte[] getResult(byte[] result) {
        int value = (((result[MAGIC_NUM_LENGTH + 1] & 0xFF) << 24)
                | ((result[MAGIC_NUM_LENGTH + 2] & 0xFF) << 16)
                | ((result[MAGIC_NUM_LENGTH + 3] & 0xFF) << 8)
                | ((result[MAGIC_NUM_LENGTH + 4] & 0xFF)));
        if (value + 1 + 4 + MAGIC_NUM_LENGTH != result.length) {
            throw new RuntimeException("error send");
        }
        checkMagic(result);

        byte[] bytes = new byte[value];
        System.arraycopy(result, MAGIC_NUM_LENGTH + 5, bytes, 0, value);
        return bytes;
    }

    private void checkMagic(byte[] result) {
        int i = 0;
        while (i < MAGIC_NUM_LENGTH) {
            if (result[i] != prefix[i]) {
                throw new RuntimeException("check info error");
            }
            i++;
        }
    }

    private void initCode(byte[] code) {
        System.arraycopy(prefix, 0, code, 0, MAGIC_NUM_LENGTH);
    }

    public static void main(String[] args) {
        Codec text = Codec.text("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest".getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(text.getText(), StandardCharsets.UTF_8));

        Codec error = Codec.error("errorMsg".getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(error.getError(), StandardCharsets.UTF_8));

        Codec code = Codec.code((byte) 1);
        System.out.println(code.getCode());
    }
}
