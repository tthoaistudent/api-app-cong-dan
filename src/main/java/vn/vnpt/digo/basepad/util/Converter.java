/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.util;

import java.sql.Blob;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author nhkhanh
 */
public class Converter {
    
    public static Blob byteArrayToBlob(byte[] byteArray) {
        try {
            return new SerialBlob(byteArray);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
    
    public static byte[] blobToByteArray(Blob blob) {
        return blobToByteArray(blob, true);
    }
    
    public static byte[] blobToByteArray(Blob blob, boolean freeup) {
        try {
            byte[] bytes = blob.getBytes(1, (int) blob.length());
            if (freeup) {
                blob.free();
            }
            return bytes;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }    
}
