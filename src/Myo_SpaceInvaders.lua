scriptId = 'es.lloydtorr.spaceinvaders'

-- Lua script to play my version of Space Invaders using the Myo.
-- A lot of the code was taken from the Myo SDK docs.

-- Variables

unlocked = false

fistMade = false
referenceRoll = myo.getRoll()
currentRoll = referenceRoll

UNLOCKED_TIMEOUT = 10000

-- Effects

function moveRight()
	myo.keyboard("right_arrow","down")
    myo.debug(myo.getArm().."goRIGHT")
end

function moveLeft()
	myo.keyboard("left_arrow","down")
    myo.debug(myo.getArm().."goLEFT")
end

function fire()
	myo.keyboard("space","press")
    myo.debug("FIRE")
end

-- Helpers

function letItGo()
    myo.keyboard("right_arrow","up")
    myo.keyboard("left_arrow","up")
    myo.debug("stopMOVING")
end

function resetFist()
    fistMade = false
    referenceRoll = myo.getRoll()
    currentRoll = referenceRoll
end

function conditionallySwapWave(pose)
    if myo.getArm() == "left" then
        if pose == "waveIn" then
            pose = "waveOut"
        elseif pose == "waveOut" then
            pose = "waveIn"
        end
    end
    return pose
end

-- Unlock mechanism

function unlock()
    unlocked = true
    extendUnlock()
end

function extendUnlock()
    unlockedSince = myo.getTimeMilliseconds()
end

-- Callbacks

function onPoseEdge(pose, edge)

    -- Unlock
    pose = conditionallySwapWave(pose)

    if pose == "thumbToPinky" then
        if edge == "off" then
            -- Unlock when pose is released in case the user holds it for a while.
            unlock()
        elseif edge == "on" and not unlocked then
            -- Vibrate twice on unlock.
            -- We do this when the pose is made for better feedback.
            myo.vibrate("short")
            myo.vibrate("short")
            myo.vibrate("short")
            extendUnlock()
        end
    end

    -- Other gestures
    if unlocked and edge == "on" then

        if pose == "fingersSpread" then
            extendUnlock()
            fire()
            myo.vibrate("short")
        elseif pose == "waveOut" then
            extendUnlock()
            moveRight()
        elseif pose == "waveIn" then
            extendUnlock()
            moveLeft()
        elseif pose == "fist" and not fistMade then
            extendUnlock()
            referenceRoll = myo.getRoll()
            fistMade = true
            if myo.getXDirection() == "towardElbow" then
                referenceRoll = referenceRoll * -1
            end
        end

        if pose ~= "fist" and pose ~= "waveIn" and pose ~= "waveOut" then
            resetFist()
            letItGo()
        end
    end
end

function onPeriodic()
    -- Lock after inactivity

    currentRoll = myo.getRoll()
    if myo.getXDirection() == "towardElbow" then
        currentRoll = currentRoll * -1
    end

    if unlocked then
        -- If we've been unlocked longer than the timeout period, lock.
        -- Activity will update unlockedSince, see extendUnlock() above.
        if myo.getTimeMilliseconds() - unlockedSince > UNLOCKED_TIMEOUT then
            unlocked = false
            myo.vibrate("short")
            myo.vibrate("short")
            myo.vibrate("short")
        end
    end

    if unlocked and fistMade then
        extendUnlock()
        subtractive = currentRoll - referenceRoll
        if subtractive > 0.2 then
            moveRight()
        elseif subtractive < -0.2 then
            moveLeft()
        end
    end

end

function onForegroundWindowChange(app, title)
    -- Here we decide if we want to control the new active app.
    local wantActive = false
    activeApp = ""

    wantActive = title == "Space Invaders"
    activeApp = "Space Invaders"

    return wantActive
end

function activeAppName()
    -- Return the active app name determined in onForegroundWindowChange
    return activeApp
end

function onActiveChange(isActive)
    if not isActive then
        unlocked = false
    end
end