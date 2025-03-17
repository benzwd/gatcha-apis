import {Heart, Sword, Shield, Footprints, Sparkle} from "lucide-react";
const Card = ({ id, ownerUsername, elementalType, level, hp, atk, def, vit, xp }) => {
    return (
        <div className="w-64 rounded overflow-hidden shadow-lg bg-white p-4 border border-amber-500">
            <div className="flex justify-between items-center mb-4">
                <span className="text-gray-700 text-xs" title={id}>ID: {id.slice(-6)}</span>
                <span className="text-gray-700 text-xs">{ownerUsername}</span>
            </div>
            <div className="text-center mb-4">
                <h2 className="text-xl font-bold text-gray-800 uppercase">{elementalType}</h2>
                <p className="text-gray-600 text-sm">Level {level}</p>
            </div>
            <div className="space-y-2">
                <div className="flex justify-between">
                    <div className="flex flex-wrap items-center gap-2">
                        <Heart size={18}/>
                        <span className="text-gray-700">Santé</span>
                    </div>
                    <span className="text-gray-700">{hp}</span>
                </div>
                <div className="flex justify-between">
                    <div className="flex flex-wrap items-center gap-2">
                        <Sword size={18}/>
                        <span className="text-gray-700">Attaque</span>
                    </div>
                    <span className="text-gray-700">{atk}</span>
                </div>
                <div className="flex justify-between">
                    <div className="flex flex-wrap items-center gap-2">
                        <Shield size={18}/>
                        <span className="text-gray-700">Défense</span>
                    </div>
                    <span className="text-gray-700">{def}</span>
                </div>
                <div className="flex justify-between">
                    <div className="flex flex-wrap items-center gap-2">
                        <Footprints size={18}/>
                        <span className="text-gray-700">Vitesse</span>
                    </div>
                    <span className="text-gray-700">{vit}</span>
                </div>
                <div className="flex justify-between">
                    <div className="flex flex-wrap items-center gap-2">
                        <Sparkle size={18}/>
                        <span className="text-gray-700">Expèrience</span>
                    </div>
                    <span className="text-gray-700">{xp}</span>
                </div>
            </div>
        </div>
    );
};

export default Card;