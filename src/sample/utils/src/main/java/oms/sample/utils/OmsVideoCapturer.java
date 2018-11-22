package oms.sample.utils;

import org.webrtc.Camera1Capturer;
import org.webrtc.Camera1Enumerator;
import org.webrtc.CameraEnumerator;

import oms.base.Stream;
import oms.base.VideoCapturer;

public final class OmsVideoCapturer extends Camera1Capturer implements VideoCapturer {
    private int width, height, fps;

    private OmsVideoCapturer(String deviceName, boolean captureToTexture) {
        super(deviceName, null, captureToTexture);
    }

    public static OmsVideoCapturer create(int width, int height, int fps,
            boolean captureToTexture) {
        String deviceName = getDeviceName(captureToTexture);
        OmsVideoCapturer capturer = new OmsVideoCapturer(deviceName, captureToTexture);
        capturer.width = width;
        capturer.height = height;
        capturer.fps = fps;
        return capturer;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getFps() {
        return fps;
    }

    @Override
    public Stream.StreamSourceInfo.VideoSourceInfo getVideoSource() {
        return Stream.StreamSourceInfo.VideoSourceInfo.CAMERA;
    }

    public void switchCamera() {
        super.switchCamera(null);
    }

    public void dispose() {
        super.dispose();
    }

    private static String getDeviceName(boolean captureToTexture) {
        CameraEnumerator enumerator = new Camera1Enumerator(captureToTexture);

        String deviceName = null;
        for (String device : enumerator.getDeviceNames()) {
            if (enumerator.isFrontFacing(device)) {
                deviceName = device;
                break;
            }
        }

        return deviceName == null ? enumerator.getDeviceNames()[0] : deviceName;
    }
}
