scriptId = 'es.lloydtorr.spaceinvaders'

-- Lua script to play my version of Space Invaders using the Myo.
-- A lot of the code was taken from the Myo SDK docs.

-- Variables

unlocked = false

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

function letItGo() -- Resets keys to normal state
    myo.keyboard("right_arrow","up")
    myo.keyboard("left_arrow","up")
    myo.keyboard("space","up")
end

function conditionallySwapWave(pose) -- Changes waveIn/waveOut to be waveLeft/waveRight instead
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

    pose = conditionallySwapWave(pose)

    if pose == "thumbToPinky" then

        if not unlocked then -- Unlock
            if edge == "off" then
                unlocked = true
            elseif edge == "on" and not unlocked then
                myo.vibrate("medium")
            end
        elseif unlocked then -- Lock
            if edge == "off" then
                unlocked = false
            elseif edge == "on" and not unlocked then
                myo.vibrate("medium")
            end
        end
        
    end

    -- Other gestures
    if unlocked and edge == "on" then

        if pose == "fist" then
            fire()
            myo.vibrate("short")
        elseif pose == "waveOut" then
            moveRight()
        elseif pose == "waveIn" then
            moveLeft()
        end

    end

    if edge == "off" then
        letItGo()
    end

end

function onPeriodic()
end

function onForegroundWindowChange(app, title)
    return title == "Space Invaders"
end

function activeAppName()
    return "Space Invaders"
end

function onActiveChange(isActive)
    if not isActive then
        unlocked = false
    end
end