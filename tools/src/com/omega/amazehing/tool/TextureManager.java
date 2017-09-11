package com.omega.amazehing.tool;

public class TextureManager {

    public static void main(String[] args) {
	if (args.length == 2) { // Pack
	    System.out.println("Packed ID : " + packTextureId(Integer.valueOf(args[0]),
		    Integer.valueOf(args[1])));
	} else if (args.length == 1) { // Unpack
	    int[] _ids = unpackTextureId(Integer.valueOf(args[0]));
	    System.out.println("Atlas id : " + _ids[0] + " - Region id : " + _ids[1]);
	} else {
	    System.out.println("Args don't match !");
	}
    }

    public static int packTextureId(int atlasId, int regionId) {
	return atlasId << 24 | regionId;
    }

    public static int[] unpackTextureId(int packedId) {
	int[] _ids = new int[2];
	_ids[0] = packedId >> 24;
	_ids[1] = packedId & 0xFFFFFF;

	return _ids;
    }
}