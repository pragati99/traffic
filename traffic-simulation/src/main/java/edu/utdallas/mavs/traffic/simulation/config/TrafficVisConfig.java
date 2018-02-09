package edu.utdallas.mavs.traffic.simulation.config;

import edu.utdallas.mavs.divas.core.config.ConfigKey;

import edu.utdallas.mavs.divas.core.config.ConfigProperty;

import edu.utdallas.mavs.divas.core.config.VisConfig;



public class TrafficVisConfig

{

    public static final ConfigKey TERRAIN = ConfigKey.create("terrain");



    public static final ConfigKey WATER   = ConfigKey.create("water");



    public static final ConfigKey SKYBOX  = ConfigKey.create("skybox");



    public static void register()

    {

        VisConfig visconfig = VisConfig.getInstance();



        ConfigProperty terrain = new ConfigProperty<>(TERRAIN, false, "Run the visualizer without terrain rendering", "Terrain", false);

        visconfig.addCustomProperty(terrain);



        ConfigProperty water = new ConfigProperty<>(WATER, false, "Run the visualizer without water rendering", "Water", false);

        visconfig.addCustomProperty(water);



        ConfigProperty skybox = new ConfigProperty<>(SKYBOX, SkyboxEnum.Sunny, "Selects the skybox of the simulation", "Skybox", false);

        visconfig.addCustomProperty(skybox);



        visconfig.save();

    }

}