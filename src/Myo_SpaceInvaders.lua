scriptId = 'es.lloydtorr.spaceinvaders'

-- Lua script to play my version of Space Invaders using the Myo.
-- A lot of the code was taken from the Myo SDK docs.

-- Variables

unlocked = false

fistMade = false
referenceRoll = myo.getRoll()
currentRoll = referenceRoll

-- Effects

function moveRight()
	myo.keyboard("right_arrow","down")
end

function moveLeft()
	myo.keyboard("left_arrow","down")
end

function fire()
	myo.keyboard("space","down")
end

-- Helpers

function letItGo()
    myo.keyboard("right_arrow","up")
    myo.keyboard("left_arrow","up")
    myo.keyboard("space","up")
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

-- Callbacks

function onPoseEdge(pose, edge)

    -- Unlock
    pose = conditionallySwapWave(pose)

    if pose == "thumbToPinky" then
        if not unlocked then
            if edge == "off" then
                -- Unlock when pose is released in case the user holds it for a while.
                unlocked = true
            elseif edge == "on" and not unlocked then
                -- Vibrate twice on unlock.
                -- We do this when the pose is made for better feedback.
                myo.vibrate("short")
                myo.vibrate("short")
                myo.vibrate("short")
            end
        elseif unlocked then
            if edge == "off" then
                -- Unlock when pose is released in case the user holds it for a while.
                unlocked = false
            elseif edge == "on" and not unlocked then
                -- Vibrate twice on unlock.
                -- We do this when the pose is made for better feedback.
                myo.vibrate("short")
                myo.vibrate("short")
                myo.vibrate("short")
            end
        end
    end

    -- Other gestures
    if unlocked and edge == "on" then

        if pose == "fingersSpread" then
            fire()
            myo.vibrate("short")
        elseif pose == "waveOut" then
            moveRight()
        elseif pose == "waveIn" then
            moveLeft()
        elseif pose == "fist" and not fistMade then
            referenceRoll = myo.getRoll()
            fistMade = true
            if myo.getXDirection() == "towardElbow" then
                referenceRoll = referenceRoll * -1
            end
        end

        if pose ~= "fist" and pose ~= "waveIn" and pose ~= "waveOut" and pose ~= "fingersSpread" then
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

    if unlocked and fistMade then
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