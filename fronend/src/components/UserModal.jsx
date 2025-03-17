import React, { useState } from 'react';
import { ChevronsUp } from "lucide-react";
import axios from "axios";

const UserModal = ({ user: initialUser, onClose }) => {
    const [user, setUser] = useState(initialUser);

    if (!user) return null;

    const experiencePercentage = user.experience;

    const upXp = async () => {
        const token = localStorage.getItem('token');
        try {
            const res = await axios.post('api/player/experience?xp=25', null, {
                headers: {
                    'Authorization': token,
                },
            });

            const newExperience = user.experience + 25;

            if (newExperience >= 100) {
                setUser(prevUser => ({
                    ...prevUser,
                    level: prevUser.level + 1,
                    experience: newExperience - 100,
                }));
            } else {
                setUser(prevUser => ({
                    ...prevUser,
                    experience: newExperience,
                }));
            }
        } catch (error) {
            console.error('XP error:', error.response ? error.response.data : error.message);
        }
    };

    const upLvl = async () => {
        const token = localStorage.getItem('token');
        try {
            const res = await axios.post('api/player/levelup', null, {
                headers: {
                    'Authorization': token,
                },
            });
            setUser(prevUser => ({
                ...prevUser,
                level: prevUser.level + 1,
                experience: 0,
            }));
        } catch (error) {
            console.error('Lvl error:', error.response ? error.response.data : error.message);
        }
    };

    return (
        <div className="fixed inset-0 bg-black/70 bg-opacity-50 flex items-center justify-center">
            <div className="bg-white p-6 rounded-lg shadow-lg max-w-md w-full">
                <h2 className="text-xl font-bold mb-4 text-center">Profil de l'utilisateur</h2>
                <hr className="divide mb-4" />
                <p className="mb-2">
                    <strong>Username :</strong> {user.username}
                </p>
                <p className="mb-2">
                    <strong>Niveau :</strong> {user.level}
                </p>
                <p className="mb-2">
                    <strong>Expérience :</strong> {user.experience} / 100
                </p>
                <div className="w-full bg-gray-200 rounded-full h-2.5 mb-4">
                    <div className="bg-amber-600 h-2.5 rounded-full"
                         style={{ width: `${experiencePercentage}%` }}></div>
                </div>

                <div className="flex justify-between">
                    <div className="flex gap-2">
                        <button
                            className="flex items-center justify-center px-2 py-1 bg-blue-500 text-white rounded hover:bg-blue-600 transition duration-150 ease-in-out text-xs"
                            onClick={upXp}
                        >
                            <ChevronsUp size={16} /> UP XP
                        </button>
                        <button
                            className="flex items-center justify-center px-2 py-1 bg-blue-500 text-white rounded hover:bg-blue-600 transition duration-150 ease-in-out text-xs"
                            onClick={upLvl}
                        >
                            <ChevronsUp size={16} /> UP LVL
                        </button>
                    </div>
                    <button
                        onClick={onClose}
                        className="bg-amber-600 text-white px-4 py-2 rounded hover:bg-amber-700 cursor-pointer transition-colors"
                    >
                        Fermer
                    </button>
                </div>
            </div>
        </div>
    );
};

export default UserModal;